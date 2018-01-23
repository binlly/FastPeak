package com.binlly.gankee.business.home

import android.content.ContentValues
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseMvpFragment
import com.binlly.gankee.base.widget.divider.divider.HorizontalDividerItemDecoration
import com.binlly.gankee.business.home.adapter.HomeAdapter
import com.binlly.gankee.business.web.WebActivity
import com.binlly.gankee.ext.getColor
import com.binlly.gankee.repo.database
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseMvpFragment<HomePresenter>(), HomeContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    private lateinit var adapter: HomeAdapter

    override fun getContentViewId(): Int = R.layout.fragment_home

    override fun initView() {
        adapter = HomeAdapter()
        adapter.setOnLoadMoreListener(this, recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        recycler.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(context).size(1).color(getColor(R.color.divider_color)).build())

        adapter.setMessageHandler { action, _, item: FeedAll?, _ ->
            when (action) {
                HomeAdapter.ACTION_TO_WEB -> item?.let {
                    val db = context?.database?.writableDatabase
                    context?.database?.use {
                        val values = ContentValues().apply {
                            //                        values.put("id", item.id)
                            put("_id", item.id)
                            put("createdAt", item.createdAt)
                            put("desc", item.desc)
                            put("publishedAt", item.publishedAt)
                            put("source", item.source)
                            put("type", item.type)
                            put("url", item.url)
                            put("who", item.who)
                            put("image_url", item.images?.get(0))
                            put("browseAt", System.currentTimeMillis())
                        }
                        db?.insert("browse_history", null, values)
                    }
                    val intent = Intent(context, WebActivity::class.java)
                    intent.putExtra("url", item.url)
                    intent.putExtra("title", item.desc)
                    startActivity(intent)
                }
            }
        }

        swipe.setOnRefreshListener {
            P.refresh()
        }
        P.refresh()
    }

    override fun onReload() {
        super.onReload()
        P.refresh()
    }

    override fun refresh(list: List<FeedAll>) {
        swipe.isRefreshing = false
        adapter.setNewData(list)
    }

    override fun onLoadMoreRequested() {
        P.loadmore()
    }

    override fun loadMore(list: List<FeedAll>) {
        adapter.addData(list)
    }

    override fun loadComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadFail() {
        swipe.isRefreshing = false
        adapter.loadMoreFail()
    }
}
