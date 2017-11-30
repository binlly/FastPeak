package ${applicationPackage}.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
class PresenterModules {

        //将此处拷贝到di目录PresenterModules类下
        @Provides
        fun provide${moduleName}Presenter(fragment: ${moduleName}Fragment): ${moduleName}Presenter {
        return ${moduleName}Presenter(fragment.context, fragment)
    }
}
