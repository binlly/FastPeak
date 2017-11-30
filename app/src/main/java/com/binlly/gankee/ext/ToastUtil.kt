package com.binlly.gankee.ext

import android.app.Activity
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.Toast
import com.binlly.gankee.service.Services

/**
 * Created by yy on 2017/8/23.
 */
object ToastUtils {

    private var toast: Toast? = null

    @JvmStatic
    fun showToast(msg: CharSequence) {
        if (msg.isEmpty()) return
        toast?.cancel()
        toast = Toast.makeText(Services.app(), msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun Activity.showToast(msg: CharSequence) {
        showToast(msg, true)
    }

    fun Activity.showToast(msg: CharSequence, gravity: Int, offsetY: Int) {
        show(msg, true, Toast.LENGTH_SHORT, gravity, offsetY)
    }

    fun Activity.showToast(msg: CharSequence, duration: Int) {
        show(msg, true, duration)
    }

    fun Activity.showToast(msg: CharSequence, autoCancel: Boolean) {
        show(msg, autoCancel, Toast.LENGTH_SHORT)
    }

    private fun Activity.show(msg: CharSequence, autoCancel: Boolean, duration: Int) {
        if (msg.isEmpty()) return
        if (autoCancel) toast?.cancel()
        toast = Toast.makeText(this, msg, duration)
        toast?.show()
    }

    private fun Activity.show(msg: CharSequence, autoCancel: Boolean, duration: Int, gravity: Int, offsetY: Int) {
        if (msg.isEmpty()) return
        if (autoCancel) toast?.cancel()
        toast = Toast.makeText(this, msg, duration)
        toast?.setGravity(gravity, 0, offsetY)
        toast?.show()
    }

    fun Activity.showToast(bitmap: Bitmap) {
        val iv = ImageView(Services.app())
        iv.setImageBitmap(bitmap)
        val t = Toast.makeText(this, "", Toast.LENGTH_SHORT)
        t.view = iv
        t.show()
    }
}
