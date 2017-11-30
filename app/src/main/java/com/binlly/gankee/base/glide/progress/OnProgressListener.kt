package com.binlly.gankee.base.glide.progress

import com.bumptech.glide.load.engine.GlideException

/**
 * Created by sunfusheng on 2017/6/14.
 */
interface OnProgressListener {
    fun onProgress(imageUrl: String, bytesRead: Long, totalBytes: Long, isDone: Boolean,
                   exception: GlideException?)
}
