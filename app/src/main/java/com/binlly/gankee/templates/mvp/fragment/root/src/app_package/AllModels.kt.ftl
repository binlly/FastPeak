package ${applicationPackage}.di

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class AllModules {

    //将此处拷贝到di目录AllModules类下
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(PresenterModules::class)) abstract
            fun contribute${moduleName}FragmentInjector(): ${moduleName}Fragment
}
