package com.binlly.gankee

import android.os.Build
import com.binlly.gankee.app.App
import org.junit.runners.model.InitializationError
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by yy on 2017/9/20.
 */

class CustomRobolectricTestRunner @Throws(InitializationError::class) constructor(
        testClass: Class<*>): RobolectricTestRunner(testClass) {

    private val buildVariant = (if (BuildConfig.FLAVOR.isEmpty()) "" else BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE

//    init {
//        System.setProperty("android.package", BuildConfig.APPLICATION_ID)
//        System.setProperty("android.manifest", "build/intermediates/manifests/full/$buildVariant/AndroidManifest.xml")
//        System.setProperty("android.resources", "build/intermediates/res/" + buildVariant)
//        System.setProperty("android.assets", "build/intermediates/assets/" + buildVariant)
//    }

    override fun buildGlobalConfig(): Config {
        return Config.Builder.defaults()
                .setApplication(App::class.java)
                .setSdk(Build.VERSION_CODES.O)
                .setConstants(BuildConfig::class.java)
                .setPackageName(BuildConfig.APPLICATION_ID)
                .setManifest("AndroidManifest.xml")
                .setResourceDir("build/intermediates/res/merged/"+buildVariant)
                .setAssetDir("build/intermediates/assets/debug"+buildVariant)
                .build()
    }
}
