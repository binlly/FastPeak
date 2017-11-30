package com.binlly.gankee.business.demo

import com.binlly.gankee.CustomRobolectricTestRunner
import com.binlly.gankee.base.rx.RxObserver
import com.binlly.gankee.business.demo.activity.DemoActivity
import com.binlly.gankee.business.demo.activity.DemoPresenter
import com.binlly.gankee.business.demo.model.DemoModel
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import kotlin.test.assertNotNull


/**
 * Created by yy on 2017/9/19.
 */
//@Config(constants = BuildConfig::class,
//        sdk = intArrayOf(Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.LOLLIPOP,
//                Build.VERSION_CODES.M, Build.VERSION_CODES.N),
//        manifest = "src/main/AndroidManifest.xml")
@RunWith(CustomRobolectricTestRunner::class)
class DemoPresenterTest {

    private lateinit var demoPresenter: DemoPresenter
    private lateinit var demoActivity: DemoActivity

    @Before
    fun setUp() {
        demoActivity = Robolectric.setupActivity(DemoActivity::class.java)
        demoPresenter = DemoPresenter(demoActivity, demoActivity)
        RxAndroidPlugins.initMainThreadScheduler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }

    @Test
    fun requestDemo() {
        demoPresenter.requestDemo(object: RxObserver<DemoModel>() {
            override fun onNext(model: DemoModel) {
                println(model)
                assertNotNull(model)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                assert(true)
            }

            override fun onComplete() {
                super.onComplete()
                assert(true)
            }
        })
    }
}