package com.binlly.gankee.base.glide.progress

import android.widget.ImageView
import com.bumptech.glide.load.engine.GlideException
import okhttp3.OkHttpClient
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

object ProgressManager {

    private val listeners = ConcurrentHashMap<WeakReference<ImageView>, WeakReference<OnProgressListener>>()

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().addNetworkInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response.newBuilder().body(
                    ProgressResponseBody(request.url().toString(), response.body(),
                            listener)).build()
        }.build()
    }

    private val listener = object: OnProgressListener {
        override fun onProgress(imageUrl: String, bytesRead: Long, totalBytes: Long,
                                isDone: Boolean, exception: GlideException?) {
            listeners.forEach { (imageview, listener) ->
                listener.get()?.onProgress(imageUrl, bytesRead, totalBytes, isDone,
                        exception) ?: listeners.remove(imageview)
            }
        }
    }

    fun addProgressListener(imageView: ImageView, progressListener: OnProgressListener?) {
        progressListener ?: return
        removeProgressListener(imageView)
        listeners.put(WeakReference(imageView), WeakReference(progressListener))
    }

    fun removeProgressListener(imageView: ImageView) {
        listeners.forEach { (view, _) ->
            if (view.get() === imageView) {
                listeners.remove(view)
                return
            }
        }
    }

    fun clearInvalidValues() {
        listeners.forEach { (view, _) ->
            if (view.get() === null) {
                listeners.remove(view)
            }
        }
    }

    private fun findProgressListener(imageView: ImageView): OnProgressListener? {
        listeners.forEach { (view, listener) ->
            if (view.get() === imageView) {
                return listener.get()
            }
        }
        return null
    }
}
