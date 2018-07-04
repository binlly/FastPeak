package com.binlly.gankee.business.history

import android.content.Context
import com.binlly.gankee.base.mvp.BaseActivityPresenter
import com.binlly.gankee.base.mvp.SimpleListView
import com.binlly.gankee.base.rx.RxObserver
import com.binlly.gankee.business.home.FeedAll


interface HistoryContract {
    interface View: SimpleListView<FeedAll> {

    }

    interface Presenter: BaseActivityPresenter {
        fun request(context: Context, page: Int, observer: RxObserver<List<FeedAll>?>)
        fun refresh()
        fun loadmore()
    }
}
