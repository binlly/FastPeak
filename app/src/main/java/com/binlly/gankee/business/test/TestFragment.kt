package com.binlly.gankee.business.test

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.binlly.gankee.Build
import com.binlly.gankee.Build.ENV_DEBUG
import com.binlly.gankee.Build.ENV_ONLINE
import com.binlly.gankee.BuildConfig
import com.binlly.gankee.R
import com.binlly.gankee.base.Device
import com.binlly.gankee.base.mvp.BaseFragment
import com.binlly.gankee.base.net.RetrofitConfig
import com.binlly.gankee.business.test.adapter.TestAdapter
import com.binlly.gankee.business.test.model.TestModel
import com.binlly.gankee.repo.RemoteRepo
import com.binlly.gankee.service.Services
import kotlinx.android.synthetic.main.fragment_test.*
import java.util.*

/**
 * 工程模式
 * Created by songshimin on 3/13/17.
 */

class TestFragment: BaseFragment() {
    private lateinit var mAdapter: TestAdapter

    override fun handleArguments(arg: Bundle?) {

    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_test
    }

    override fun initView() {
        mAdapter = TestAdapter(context, null, this)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = mAdapter

        mAdapter.setNewData(testData)
        setPageSuccess()
    }

    fun refresh() {
        mAdapter.setNewData(testData)
    }

    private val testData: List<TestModel>
        get() {
            val datas = ArrayList<TestModel>()
            datas.add(getSection("选择环境"))

            datas.add(getEnvModel(ENV_DEBUG, "测试环境"))
            datas.add(getEnvModel(ENV_ONLINE, "线上环境"))

            if (BuildConfig.DEBUG) { //只在测试环境下打开此项设置
                datas.add(getSection("设置Mock服务器(即时生效)"))
                datas.add(getMockModel("mockHost", RemoteRepo.mockHost))
            }

            val router = Services.remoteConfig().getRouter()
            if (BuildConfig.DEBUG && router.isNotEmpty()) { //只在测试环境下打开此项设置
                datas.add(getSection("Mock路由表"))
                for ((key, value) in router) datas.add(getRouterModel(key, value.toString()))
            }

            datas.add(getSection("版本信息"))
            datas.add(getBuildModel("ApplicationId", BuildConfig.APPLICATION_ID))
            datas.add(getBuildModel("Version", BuildConfig.VERSION_NAME))
            datas.add(getBuildModel("BuildType", BuildConfig.BUILD_TYPE))
            datas.add(getBuildModel("APIV", Device.getAPIV(Services.app)))
            datas.add(getBuildModel("Host", RetrofitConfig.getBaseUrl()))
            datas.add(getBuildModel("IMSI", Device.getIMSI(Services.app)))
            datas.add(getBuildModel("IP", Device.getIpAddress(Services.app)))
            datas.add(getBuildModel("mac", Device.getLocalMacAddress(Services.app)))

            return datas
        }

    private fun getSection(text: String): TestModel {
        val model = TestModel()
        model.section = text
        model.item_type = TestModel.TYPE_SECTION
        return model
    }

    private fun getEnvModel(key: String, value: String): TestModel {
        val env = Build.env
        val model = TestModel()
        val envModel = TestModel.EnvModel(key, value, env == key)
        model.env = envModel
        model.item_type = TestModel.TYPE_ENV
        return model
    }

    private fun getBuildModel(key: String, value: String): TestModel {
        val model = TestModel()
        val buildModel = TestModel.BuildModel(key, value)
        model.valueColor = Color.parseColor("#222222")
        model.build = buildModel
        model.item_type = TestModel.TYPE_BUILD
        return model
    }

    private fun getMockModel(key: String, value: String): TestModel {
        val model = TestModel()
        val mockModel = TestModel.MockModel(key, value)
        model.valueColor = Color.parseColor("#222222")
        model.mock = mockModel
        model.item_type = TestModel.TYPE_MOCK
        return model
    }

    private fun getRouterModel(key: String, value: String): TestModel {
        val model = TestModel()
        val mockModel = TestModel.RouterModel(key, value)
        model.valueColor = Color.parseColor("#222222")
        model.router = mockModel
        model.item_type = TestModel.TYPE_ROUTER
        return model
    }
}
