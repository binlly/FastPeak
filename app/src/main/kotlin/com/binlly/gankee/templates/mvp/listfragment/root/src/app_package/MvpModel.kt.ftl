package ${packageName}

import com.chad.library.adapter.base.entity.MultiItemEntity

data class ${moduleName}Model(val list: MutableList<Model>): IModel {
    companion object {
        const val TYPE_1 = 0
        const val TYPE_2 = 1
    }

    data class Model(val b: Boolean): MultiItemEntity {
        override fun getItemType(): Int = if (b) 1 else 0
    }
}
