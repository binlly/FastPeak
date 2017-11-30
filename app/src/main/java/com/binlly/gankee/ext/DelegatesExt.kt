package com.binlly.gankee.ext

import com.binlly.gankee.base.Preference
import com.binlly.gankee.service.Services

/**
 * Created by yy on 2017/9/1.
 */


object DelegatesExt {
    fun <T: Any> preference(which: String, key: String, default: T) = Preference(Services.app,
            which, key, default)
}