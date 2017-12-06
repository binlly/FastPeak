package com.binlly.gankee.business.debug.model

import com.chad.library.adapter.base.entity.MultiItemEntity

import java.io.Serializable

class DebugModel: MultiItemEntity, Serializable {
    var item_type: Int = 0

    val isSectionType: Boolean
        get() = item_type == TYPE_SECTION

    val isEnvType: Boolean
        get() = item_type == TYPE_ENV

    val isBuildType: Boolean
        get() = item_type == TYPE_BUILD

    val isMockType: Boolean
        get() = item_type == TYPE_MOCK

    val isRouterType: Boolean
        get() = item_type == TYPE_ROUTER


    override fun getItemType(): Int {
        return item_type
    }

    lateinit var section: String
    lateinit var env: EnvModel
    lateinit var build: BuildModel
    lateinit var mock: MockModel
    lateinit var router: RouterModel

    var valueColor: Int = 0

    data class EnvModel(var key: String, var value: String, var isSelected: Boolean = false)

    data class BuildModel(var key: String, var value: String)

    data class MockModel(var key: String, var value: String)

    data class RouterModel(var key: String, var value: String)

    companion object {
        private const val serialVersionUID = -4241242337755076267L

        val TYPE_SECTION = 0
        val TYPE_ENV = 1
        val TYPE_BUILD = 2
        val TYPE_MOCK = 3
        val TYPE_ROUTER = 4
    }
}