package com.binlly.gankee.business.history

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseMvpActivity
import com.binlly.gankee.base.widget.divider.divider.HorizontalDividerItemDecoration
import com.binlly.gankee.business.home.FeedAll
import com.binlly.gankee.business.home.adapter.HistoryAdapter
import com.binlly.gankee.business.home.adapter.HomeAdapter
import com.binlly.gankee.business.web.WebActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_root_delegate.*


class HistoryActivity: BaseMvpActivity<HistoryPresenter>(), HistoryContract.View,
                       BaseQuickAdapter.RequestLoadMoreListener {

    override fun getContentView(): Int {
        return R.layout.activity_history
    }

    override fun isNeedToolbar(): Boolean {
        return true
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.title = "浏览历史"
        adapter = HistoryAdapter()
        adapter.setOnLoadMoreListener(this, recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).size(1).color(
                resources.getColor(R.color.divider_color)).build())

        adapter.setEmptyView(R.layout.loading_page_empty)

        adapter.setMessageHandler { action, _, item: FeedAll?, _ ->
            when (action) {
                HomeAdapter.ACTION_TO_WEB -> item?.let {
                    val intent = Intent(this, WebActivity::class.java)
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

    private lateinit var adapter: HistoryAdapter

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

    override fun setPageSuccess() {

    }

    override fun setPageError() {

    }

    override fun setPageEmpty() {

    }
}
