package com.binlly.gankee.business.girl

import com.binlly.gankee.base.mvp.BaseFragmentPresenter
import com.binlly.gankee.base.mvp.SimpleListView
import com.binlly.gankee.base.rx.RxObserver


interface GirlContract {
    interface View: SimpleListView<FeedGirl> {

    }

    interface Presenter: BaseFragmentPresenter {
        fun requestGirls(page: Int, observer: RxObserver<List<FeedGirl>?>)
    }
}
