package ${packageName}

import android.os.Bundle
<#if applicationPackage?? && generateLayout>
import ${applicationPackage}.R
</#if>

class ${moduleName}Fragment: BaseMvpFragment<${moduleName}Presenter>(), ${moduleName}Contract.View {
    override fun getContentViewId(): Int {
        return R.layout.${layoutName}
    }

    override fun initView() {
        refresh()
    }

    override fun onReload() {
        super.onReload()
        refresh()
    }

    private fun refresh() {
        setPageLoading()
        P.request(object: RxObserver<${moduleName}Model>() {
            override fun onNext(model: ${moduleName}Model) {
                setPageSucceed()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                setPageError()
            }

            override fun onComplete() {
                super.onComplete()
            }
        })
    }
}
