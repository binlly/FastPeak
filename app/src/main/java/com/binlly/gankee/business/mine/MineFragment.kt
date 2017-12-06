package com.binlly.gankee.business.mine

import android.os.Bundle
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseFragment
import com.binlly.gankee.business.debug.DebugActivity
import com.binlly.gankee.tools.MultiClicker
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.startActivity

/**
 * Created by yy on 2017/12/6.
 */
class MineFragment: BaseFragment() {

    override fun handleArguments(arg: Bundle?) {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        //多次点击进入工程模式
        val multiClicker = MultiClicker()
        multiClicker.onMultiClick(view = image_top) {
            context.startActivity<DebugActivity>()
        }
        
        setPageSuccess()
    }
}