package com.binlly.gankee.business.girl

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.SharedElementCallback
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseMvpFragment
import com.binlly.gankee.base.widget.divider.divider.HorizontalDividerItemDecoration
import com.binlly.gankee.business.home.adapter.GirlAdapter
import com.binlly.gankee.business.photo.PhotoViewActivity
import com.binlly.gankee.ext.dp2px
import com.binlly.gankee.ext.getColor
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class GirlFragment: BaseMvpFragment<GirlPresenter>(), GirlContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    private lateinit var adapter: GirlAdapter

    override fun getContentViewId(): Int = R.layout.fragment_girl

    override fun initView() {
        adapter = GirlAdapter()
        adapter.setOnLoadMoreListener(this, recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        val h = 10
        recycler.addItemDecoration(HorizontalDividerItemDecoration.Builder(context).size(h.dp2px()).color(
                getColor(R.color.divider_color)).build())

        adapter.setMessageHandler { action, position, item, args, view ->
            when (action) {
                GirlAdapter.ACTION_TO_PREVIEW -> {
                    val intent = Intent(context, PhotoViewActivity::class.java)
                    intent.putExtra("position", position)
                    intent.putStringArrayListExtra("photo_list", getPhotoList() as ArrayList<String>?)
                    enterPosition = position
                    exitPosition = position
                    try {
                        val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                context as Activity, view as View, getString(R.string.transform_name_photo, position))
                        startActivity(intent, activityOptionsCompat.toBundle())
                    } catch (e: Exception) {
                        startActivity(intent)
                    }
                    setExitSharedElementCallback()
                }
            }
        }

        swipe.setOnRefreshListener {
            P.refresh()
        }
        P.refresh()
    }

    private var enterPosition: Int = 0
    private var exitPosition: Int = 0

    override fun isNeedEventBus(): Boolean {
        return true
    }

    private fun setExitSharedElementCallback() {
        val act = activity
        act ?: return
        ActivityCompat.setExitSharedElementCallback(act, object: SharedElementCallback() {
            override fun onMapSharedElements(
                    names: MutableList<String>, sharedElements: MutableMap<String, View>
            ) {
                if (exitPosition != enterPosition && names.size > 0) {
                    names.clear()
                    sharedElements.clear()
                    val view = recycler.layoutManager.findViewByPosition(exitPosition)
                    if (view != null) {
                        view.transitionName = getString(R.string.transform_name_photo, exitPosition)
                        names.add(view.transitionName)
                        sharedElements[view.transitionName] = view
                    }
                }
                ActivityCompat.setExitSharedElementCallback(act, null)
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPageChanged(event: PageChangedEvent) {
        exitPosition = event.exitPosition
        recycler.scrollToPosition(event.exitPosition)
    }

    private fun getPhotoList(): List<String?> {
        return adapter.data.filterNotNull().map { it.url }
    }

    override fun onReload() {
        super.onReload()
        swipe.isRefreshing = false
        P.refresh()
    }

    override fun onLoadMoreRequested() {
        P.loadmore()
    }

    override fun refresh(list: List<FeedGirl>) {
        swipe.isRefreshing = false
        adapter.setNewData(list)
    }

    override fun loadMore(list: List<FeedGirl>) {
        adapter.addData(list)
    }

    override fun loadEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadFail() {
        adapter.loadMoreFail()
    }
}
