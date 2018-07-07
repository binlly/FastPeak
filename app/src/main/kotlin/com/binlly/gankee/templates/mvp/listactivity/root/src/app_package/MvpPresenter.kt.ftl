package ${packageName}

import android.content.Context
import android.content.Intent

class ${moduleName}Presenter(context: Context, mView: ${moduleName}Contract.View):
        BaseActivityPresenterImpl<${moduleName}Contract.View>(context, mView),
        ${moduleName}Contract.Presenter {

    override fun handleIntent(intent: Intent): Boolean {
        return true
    }

    private var page = 1

    override fun request(page: Int, observer: RxObserver<List<${moduleName}Model.Model>?>) {
        ${moduleName}Repo.request(page, observer)
    }

    override fun refresh() {
        page = 1
        request(1, object: RxObserver<List<${moduleName}Model.Model>?>() {
            override fun onNext(list: List<${moduleName}Model.Model>?) {
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

    override fun loadMore() {
        ++page
        request(page, object: RxObserver<List<${moduleName}Model.Model>?>() {
            override fun onNext(list: List<${moduleName}Model.Model>?) {
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