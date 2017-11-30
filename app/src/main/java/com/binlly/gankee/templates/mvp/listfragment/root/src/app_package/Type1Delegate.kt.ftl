package ${packageName}.adapter

import android.content.Context
import android.widget.TextView
import ${applicationPackage}.R
import ${applicationPackage}.base.adapter.BaseDelegate
import com.chad.library.adapter.base.BaseViewHolder

class Type1Delegate(context: Context): BaseDelegate<${moduleName}Model.Model>(context) {

    override val layoutResId: Int
        get() = R.layout.xxx

    override fun childConvert(holder: BaseViewHolder, item: ${moduleName}Model.Model) {

    }
}