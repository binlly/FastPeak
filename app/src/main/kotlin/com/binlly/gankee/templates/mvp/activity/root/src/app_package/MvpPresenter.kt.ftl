package ${packageName}

import android.content.Context
import android.content.Intent

class ${moduleName}Presenter(context: Context, mView: ${moduleName}Contract.View):
        BaseActivityPresenterImpl<${moduleName}Contract.View>(context, mView),
        ${moduleName}Contract.Presenter {

    override fun handleIntent(intent: Intent): Boolean {
        return true
    }

    override fun request(observer: RxObserver<${moduleName}Model>) {
        ${moduleName}Repo.request(observer)
    }
}
