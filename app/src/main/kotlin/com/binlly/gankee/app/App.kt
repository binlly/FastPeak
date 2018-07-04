package com.binlly.gankee.app

import android.app.Activity
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.binlly.gankee.BuildConfig
import com.binlly.gankee.base.logger.LogLevel
import com.binlly.gankee.base.logger.logLevel
import com.binlly.gankee.base.logger.logable
import com.binlly.gankee.business.home.HomeActivity
import com.binlly.gankee.di.DaggerAppComponent
import com.binlly.gankee.service.Services
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Created by yy on 2017/8/23.
 */
class App: Application(), HasActivityInjector, HasSupportFragmentInjector,
           Application.ActivityLifecycleCallbacks {

    @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    val activitys: MutableList<Activity> = ArrayList()

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragmentInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().create(this).inject(this)
        app = this
        Services.app = app
        initLogger()
    }

    private fun initLogger() {
        logable = BuildConfig.DEBUG
        logLevel = LogLevel.DEBUG
    }

    companion object {
        lateinit var app: App
            private set
        var mIsAppForeground: Boolean = false
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        activitys.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        mIsAppForeground = true
    }

    override fun onActivityPaused(activity: Activity) {
        mIsAppForeground = false
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        activitys.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
    }

    private fun finishAllActivities() {
        activitys.forEach { it.finish() }
    }

    fun exit() {
        finishAllActivities()
        System.exit(0)
    }

    fun restartApp(context: Context) {
        val mStartActivity = Intent(context, HomeActivity::class.java)
        val mPendingIntentId = 1234567
        val mPendingIntent = PendingIntent.getActivity(context,
                mPendingIntentId,
                mStartActivity,
                PendingIntent.FLAG_CANCEL_CURRENT)
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent)
        exit()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}