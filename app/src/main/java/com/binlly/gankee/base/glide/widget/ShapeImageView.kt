package com.binlly.gankee.base.glide.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import com.binlly.gankee.R
import com.binlly.gankee.base.glide.DisplayUtil

// 定义Bitmap的默认配置
private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
private val COLOR_DRAWABLE_DIMENSION = 1

open class ShapeImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                    defStyleAttr: Int = 0): ImageView(context,
        attrs, defStyleAttr) {

    // 图片的宽高
    private var w: Int = 0
    private var h: Int = 0

    private var borderColor = 0x1A000000 // 边框颜色
    private var borderWidth = 0 // 边框宽度
    private var radius = 0 // 圆角弧度
    private var shapeType = ShapeType.RECTANGLE // 图片类型（圆形, 矩形）

    private var pressedPaint: Paint? = null // 按下的画笔
    private var pressedAlpha = 0.1f // 按下的透明度
    private var pressedColor = 0x1A000000 // 按下的颜色

    enum class ShapeType(value: Int) {
        RECTANGLE(0), CIRCLE(1)
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageViewStyle)
            borderWidth = array.getDimensionPixelOffset(
                    R.styleable.ShapeImageViewStyle_siv_border_width, borderWidth)
            borderColor = array.getColor(R.styleable.ShapeImageViewStyle_siv_border_color,
                    borderColor)
            radius = array.getDimensionPixelOffset(R.styleable.ShapeImageViewStyle_siv_radius,
                    radius)
            pressedAlpha = array.getFloat(R.styleable.ShapeImageViewStyle_siv_pressed_alpha,
                    pressedAlpha)
            if (pressedAlpha > 1) pressedAlpha = 1f
            pressedColor = array.getColor(R.styleable.ShapeImageViewStyle_siv_pressed_color,
                    pressedColor)
            shapeType = when (array.getInteger(R.styleable.ShapeImageViewStyle_siv_shape_type, 0)) {
                1 -> ShapeType.CIRCLE
                else -> ShapeType.RECTANGLE
            }
            array.recycle()
        }

        initPressedPaint()
        isClickable = true
        isDrawingCacheEnabled = true
        setWillNotDraw(false)
    }

    // 初始化按下的画笔
    private fun initPressedPaint() {
        pressedPaint = Paint()
        pressedPaint!!.isAntiAlias = true
        pressedPaint!!.style = Paint.Style.FILL
        pressedPaint!!.color = pressedColor
        pressedPaint!!.alpha = 0
        pressedPaint!!.flags = Paint.ANTI_ALIAS_FLAG
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable ?: return

        if (getWidth() == 0 || getHeight() == 0) {
            return
        }

        drawDrawable(canvas, getBitmapFromDrawable(drawable))
        drawBorder(canvas)
        drawPressed(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w
        this.h = h
    }

    // 绘制圆角
    private fun drawDrawable(canvas: Canvas, bitmap: Bitmap?) {
        var bitmap = bitmap
        val paint = Paint()
        paint.color = 0xffffffff.toInt()
        paint.isAntiAlias = true

        val saveFlags = Canvas.MATRIX_SAVE_FLAG or Canvas.CLIP_SAVE_FLAG or Canvas.HAS_ALPHA_LAYER_SAVE_FLAG or Canvas.FULL_COLOR_LAYER_SAVE_FLAG or Canvas.CLIP_TO_LAYER_SAVE_FLAG

        canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, saveFlags)

        if (shapeType == ShapeType.RECTANGLE) {
            val rectf = RectF((borderWidth / 2).toFloat(), (borderWidth / 2).toFloat(),
                    (getWidth() - borderWidth / 2).toFloat(),
                    (getHeight() - borderWidth / 2).toFloat())
            canvas.drawRoundRect(rectf, radius.toFloat(), radius.toFloat(), paint)
        } else {
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(),
                    (width / 2 - borderWidth).toFloat(), paint)
        }

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) // SRC_IN 只显示两层图像交集部分的上层图像

        //Bitmap缩放
        val scaleWidth = getWidth().toFloat() / bitmap!!.width
        val scaleHeight = getHeight().toFloat() / bitmap.height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
        canvas.restore()
    }

    // 绘制边框
    private fun drawBorder(canvas: Canvas) {
        if (borderWidth > 0) {
            val paint = Paint()
            paint.strokeWidth = borderWidth.toFloat()
            paint.style = Paint.Style.STROKE
            paint.color = borderColor
            paint.isAntiAlias = true
            if (shapeType == ShapeType.RECTANGLE) {
                val rectf = RectF((borderWidth / 2).toFloat(), (borderWidth / 2).toFloat(),
                        (getWidth() - borderWidth / 2).toFloat(),
                        (getHeight() - borderWidth / 2).toFloat())
                canvas.drawRoundRect(rectf, radius.toFloat(), radius.toFloat(), paint)
            } else {
                canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(),
                        ((width - borderWidth) / 2).toFloat(), paint)
            }
        }
    }

    // 绘制按下效果
    private fun drawPressed(canvas: Canvas) {
        if (shapeType == ShapeType.RECTANGLE) {
            val rectf = RectF(1f, 1f, (width - 1).toFloat(), (height - 1).toFloat())
            canvas.drawRoundRect(rectf, radius.toFloat(), radius.toFloat(), pressedPaint!!)
        } else {
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width / 2).toFloat(),
                    pressedPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressedPaint!!.alpha = (pressedAlpha * 255).toInt()
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                pressedPaint!!.alpha = 0
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
            }
            else -> {
                pressedPaint!!.alpha = 0
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    // 获取Bitmap内容
    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap? {
        try {
            val bitmap: Bitmap
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            } else if (drawable is ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION,
                        BITMAP_CONFIG)
            } else {
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
                        BITMAP_CONFIG)
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            return null
        }

    }

    // 设置边框颜色
    fun setBorderColor(@ColorRes id: Int) {
        this.borderColor = resources.getColor(id)
        invalidate()
    }

    // 设置边框宽度
    fun setBorderWidth(borderWidth: Int) {
        this.borderWidth = DisplayUtil.dip2px(context, borderWidth.toFloat())
        invalidate()
    }

    // 设置图片按下颜色透明度
    fun setPressedAlpha(pressAlpha: Float) {
        this.pressedAlpha = pressAlpha
    }

    // 设置图片按下的颜色
    fun setPressedColor(@ColorRes id: Int) {
        this.pressedColor = resources.getColor(id)
        pressedPaint!!.color = pressedColor
        pressedPaint!!.alpha = 0
        invalidate()
    }

    // 设置圆角半径
    fun setRadius(radius: Int) {
        this.radius = DisplayUtil.dip2px(context, radius.toFloat())
        invalidate()
    }

    // 设置形状类型
    fun setShapeType(shapeType: ShapeType) {
        this.shapeType = shapeType
        invalidate()
    }
}
