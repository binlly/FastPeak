package com.binlly.gankee.tools

import android.os.SystemClock
import android.view.View


/**
 * Created by yy on 2017/9/1.
 */

class MultiClicker {
    var mHits = LongArray(5)

    fun onMultiClick(time: Int = 600, hit: Int = 5, view: View? = null, run: () -> Unit) {
        mHits = LongArray(hit)
        view?.setOnClickListener {
            System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
            mHits[mHits.size - 1] = SystemClock.uptimeMillis()
            if (mHits[0] > SystemClock.uptimeMillis() - time) {
                run.invoke()
            }
        }
    }
}