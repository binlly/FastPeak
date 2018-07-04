package com.binlly.gankee.business.history

import android.content.Context
import android.content.Intent
import com.binlly.gankee.base.mvp.BaseActivityPresenterImpl
import com.binlly.gankee.base.rx.RxObserver
import com.binlly.gankee.business.home.FeedAll
import com.binlly.gankee.repo.HistoryRepo

class HistoryPresenter(context: Context, mView: HistoryContract.View):
        BaseActivityPresenterImpl<HistoryContract.View>(context, mView), HistoryContract.Presenter {

    private var page: Int = 0

    override fun handleIntent(intent: Intent): Boolean {
        return true
    }

    override fun request(context: Context, page: Int, observer: RxObserver<List<FeedAll>?>) {
        HistoryRepo.requestHistory(context, page, observer)
    }

    override fun refresh() {
        page = 0
        request(context, page, object: RxObserver<List<FeedAll>?>() {
            override fun onNext(list: List<FeedAll>?) {
                if (list == null || list.isEmpty()) {
                    V().setPageEmpty()
                    return
                }
                V().setPageSuccess()
                V().refresh(list)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                V().loadFail()
            }
        })
    }

    override fun loadmore() {
        ++page
        request(context, page, object: RxObserver<List<FeedAll>?>() {
            override fun onNext(list: List<FeedAll>?) {
                if (list == null || list.isEmpty()) {
                    V().loadEnd()
                    return
                }
                V().loadMore(list)
                V().loadComplete()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                V().loadFail()
            }
        })
    }
}
