package com.binlly.gankee.ext

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.binlly.gankee.R
import com.binlly.gankee.base.glide.DisplayUtil
import com.binlly.gankee.base.glide.progress.OnGlideImageViewListener
import com.binlly.gankee.base.glide.progress.OnProgressListener
import com.binlly.gankee.base.glide.progress.ProgressManager
import com.binlly.gankee.service.Services
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


/**
 * Created by yy on 2017/8/29.
 */

private val mMainHandler = Handler(Looper.getMainLooper())

private val screenHeight = DisplayUtil.getScreenHeight(Services.app)
private val screenWidth = DisplayUtil.getScreenWidth(Services.app)

fun ImageView.loadNoCache(
        uri: Any?,
        holder: Int = getColor(R.color.place_holder),
        listener: OnGlideImageViewListener? = null,
        onReady: ((resource: Drawable?, e: GlideException?) -> Boolean)? = null
) {
    val options = RequestOptions().placeholder(holder).error(holder).diskCacheStrategy(DiskCacheStrategy.NONE)
    load(uri, options, null, listener, onReady)
}

fun ImageView.loadCircle(
        uri: Any?,
        holder: Int = getColor(R.color.place_holder),
        listener: OnGlideImageViewListener? = null,
        onReady: ((resource: Drawable?, e: GlideException?) -> Boolean)? = null
) {
    val options = RequestOptions().placeholder(holder).error(holder).circleCrop()
    load(uri, options, null, listener, onReady)
}

fun ImageView.load(
        uri: Any?,
        holder: Int = getColor(R.color.place_holder),
        listener: OnGlideImageViewListener? = null,
        onReady: ((resource: Drawable?, e: GlideException?) -> Boolean)? = null
) {
    val options = RequestOptions().placeholder(holder).error(holder)
    load(uri, options, null, listener, onReady)
}

//自动设置ImageView的宽高为下载的图片的宽高比，宽度为ImageView的最大宽度
fun ImageView.loadAuto(
        uri: Any?,
        holder: Int = getColor(R.color.place_holder),
        listener: OnGlideImageViewListener? = null
) {
    val options = RequestOptions().placeholder(holder).error(holder)
    load(uri, options, null, listener) { resource, _ ->
        resource?.let {
            layoutParams.width = -1
            layoutParams.height = ((screenWidth.toFloat() / resource.intrinsicWidth) * resource.intrinsicHeight).toInt()
            requestLayout()
        }
        return@load false
    }
}

fun ImageView.load(
        uri: Any?,
        options: RequestOptions,
        thumbnail: RequestBuilder<Drawable>? = null,
        listener: OnGlideImageViewListener? = null,
        onReady: ((resource: Drawable?, e: GlideException?) -> Boolean)? = null
) {
    if (checkUriParams(uri)) addListener(this, listener)

    val requestBuilder = Glide.with(context).load(uri).apply(options).thumbnail(thumbnail)
    requestBuilder.listener(object: RequestListener<Drawable> {
        override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
        ): Boolean {
            listener?.let { ProgressManager.removeProgressListener(this@load) }
            onReady ?: return false
            return onReady(resource, null)
        }

        override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
        ): Boolean {
            listener?.let { ProgressManager.removeProgressListener(this@load) }
            onReady ?: return false
            return onReady(null, e)
        }
    }).into(this)
}

private fun addListener(
        imageView: ImageView,
        listener: OnGlideImageViewListener?
) {
    listener?.let {
        val internalProgressListener = object: OnProgressListener {
            override fun onProgress(
                    imageUrl: String,
                    bytesRead: Long,
                    totalBytes: Long,
                    isDone: Boolean,
                    exception: GlideException?
            ) {
                mMainHandler.post {
                    val percent = (bytesRead * 1.0f / totalBytes * 100.0f).toInt()
                    listener.onProgress(percent, isDone, exception)
                }
                if (isDone) ProgressManager.removeProgressListener(imageView)
            }
        }
        ProgressManager.addProgressListener(imageView, internalProgressListener)
    }
}

private fun checkUriParams(uri: Any?): Boolean = uri?.let { (uri as? String)?.startsWith("http") } ?: false