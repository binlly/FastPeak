package ${packageName}

import android.content.Context
import android.content.Intent

class ${moduleName}Presenter(context: Context, mView: ${moduleName}Contract.View):
        BaseFragmentPresenterImpl<${moduleName}Contract.View>(context, mView),
        ${moduleName}Contract.Presenter {

    override fun handleArgument(bundle: Bundle?) {
        super.handleArgument(bundle)
    }

    override fun request(observer: RxObserver<${moduleName}Model>) {
        ${moduleName}Repo.request(observer)
    }
}
