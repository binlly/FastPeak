package ${packageName}

import android.os.Bundle
<#if includeCppSupport!false>
import android.widget.TextView
</#if>
<#if applicationPackage?? && generateLayout>
import ${applicationPackage}.R
</#if>

import kotlinx.android.synthetic.main.${layoutName}.*


class ${moduleName}Activity: BaseMvpActivity<${moduleName}Presenter>(), ${moduleName}Contract.View {

<#if generateLayout>
        override fun getContentView(): Int {
            return R.layout.${layoutName}
        }
</#if>

    override fun initView(savedInstanceState: Bundle?) {
        refresh()
    }

    fun refresh() {
        P().request(object: RxObserver<${moduleName}Model>(this) {
            override fun onNext(model: ${moduleName}Model) {
                toast(model.toString())
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }

            override fun onComplete() {
                super.onComplete()
            }
        })
    }
}
