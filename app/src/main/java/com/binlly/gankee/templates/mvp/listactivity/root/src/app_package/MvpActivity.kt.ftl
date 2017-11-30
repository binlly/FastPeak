package ${packageName}

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
<#if includeCppSupport!false>
</#if>
<#if applicationPackage?? && generateLayout>
import ${applicationPackage}.R
</#if>

import kotlinx.android.synthetic.main.${layoutName}.*


class ${moduleName}Activity: BaseMvpActivity<${moduleName}Presenter>(), ${moduleName}Contract.View {

    private lateinit var adapter: ${moduleName}Adapter

<#if generateLayout>
    override fun getContentView(): Int = R.layout.${layoutName}
</#if>

    override fun initView(savedInstanceState: Bundle?) {
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = ${moduleName}Adapter(this)
        adapter.setEnableLoadMore(true)
        adapter.setOnLoadMoreListener({ P.loadMore() }, recycler)
        recycler.adapter = adapter
        recycler.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).size(1).color(
        getColorExt(R.color.divider_color)).build())

        swipe.setOnRefreshListener {
            P.refresh()
        }
        P.refresh()
    }

    override fun refresh(list: List<${moduleName}Model.Model>) {
        swipe.isRefreshing = false
        adapter.setNewData(list)
    }

    override fun loadMore(list: List<${moduleName}Model.Model>) {
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

    override fun customTitle(): String = ""
}
