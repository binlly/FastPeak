package com.binlly.gankee.base.glide.progress

import com.bumptech.glide.load.engine.GlideException

interface OnGlideImageViewListener {

    fun onProgress(percent: Int, isDone: Boolean, exception: GlideException?)
}
