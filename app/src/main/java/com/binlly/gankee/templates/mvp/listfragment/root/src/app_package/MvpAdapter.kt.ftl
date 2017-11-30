package ${packageName}.adapter

import android.content.Context
import ${applicationPackage}.base.adapter.MultipleItemAdapter

class ${moduleName}Adapter(context: Context): MultipleItemAdapter<${moduleName}Model.Model>(null) {

    init {
        addItemViewDelegate(${moduleName}Model.TYPE_1, Type1Delegate(context))
        addItemViewDelegate(${moduleName}Model.TYPE_2, Type2Delegate(context))
    }
}