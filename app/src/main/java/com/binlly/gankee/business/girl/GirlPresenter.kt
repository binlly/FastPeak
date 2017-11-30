package com.binlly.gankee.business.girl

import android.content.Context
import com.binlly.gankee.base.mvp.BaseFragmentPresenterImpl
import com.binlly.gankee.base.rx.RxObserver
import com.binlly.gankee.repo.GirlRepo

class GirlPresenter(
        context: Context, mView: GirlContract.View
): BaseFragmentPresenterImpl<GirlContract.View>(context, mView), GirlContract.Presenter {

    override fun requestGirls(page: Int, observer: RxObserver<List<FeedGirl>?>) {
        GirlRepo.requestGirls(page, observer)
    }

    private var page = 1

    fun refresh() {
        page = 1
        requestGirls(1, object: RxObserver<List<FeedGirl>?>() {
            override fun onNext(list: List<FeedGirl>?) {
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

    fun loadmore() {
        ++page
        requestGirls(page, object: RxObserver<List<FeedGirl>?>() {
            override fun onNext(list: List<FeedGirl>?) {
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
