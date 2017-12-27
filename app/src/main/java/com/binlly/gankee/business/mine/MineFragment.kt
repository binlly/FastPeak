package com.binlly.gankee.business.mine

import android.content.Intent
import android.os.Bundle
import com.binlly.gankee.BuildConfig
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseFragment
import com.binlly.gankee.business.debug.DebugActivity
import com.binlly.gankee.business.history.HistoryActivity
import com.binlly.gankee.business.web.WebActivity
import com.binlly.gankee.repo.database
import com.binlly.gankee.service.Services
import com.binlly.gankee.tools.MultiClicker
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by yy on 2017/12/6.
 */

const val GitHub = "https://github.com/binlly/Gankee"

class MineFragment: BaseFragment() {

    override fun handleArguments(arg: Bundle?) {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        //多次点击进入工程模式
        val multiClicker = MultiClicker()
        multiClicker.onMultiClick(view = view_top) {
            context.startActivity<DebugActivity>()
        }

        item_version.onClick {
            context.toast("当前版本是 ${BuildConfig.VERSION_NAME}")
        }

        item_cache.onClick {
            val db = context.database.writableDatabase
            async(UI) {
                bg {
                    db.use { db.delete("browse_history", null, null) }
                    Glide.get(Services.app).clearDiskCache()
                }
                getContext().toast("清除缓存成功")
            }
        }

        item_history.onClick {
            context.startActivity<HistoryActivity>()
        }

        item_github.onClick {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url", GitHub)
            intent.putExtra("title", "GitHub")
            startActivity(intent)
        }

        setPageSuccess()
    }
}