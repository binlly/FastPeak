package com.binlly.gankee.di

import com.binlly.gankee.app.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by yy on 2017/8/24.
 * Application 依赖注入器
 */
@Singleton
@Component(modules = [AllModules::class, AndroidSupportInjectionModule::class])
interface AppComponent: AndroidInjector<App> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<App>()
}