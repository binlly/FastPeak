package com.binlly.gankee.di

import com.binlly.gankee.business.girl.GirlFragment
import com.binlly.gankee.business.girl.GirlPresenter
import com.binlly.gankee.business.home.HomeFragment
import com.binlly.gankee.business.home.HomePresenter
import dagger.Module
import dagger.Provides

/**
 * Created by yy on 2017/8/24.
 */

@Module
class PresenterModules {

    //    @Binds abstract fun provideActivity(activity: DemoActivity): DemoActivity

    //    @Module companion object {
    //    @ActivityScope
    //    @JvmStatic
    //    }

    @Provides
    fun provideHomePresenter(fragment: HomeFragment): HomePresenter {
        return HomePresenter(fragment.context, fragment)
    }

    @Provides
    fun provideGirlPresenter(fragment: GirlFragment): GirlPresenter {
        return GirlPresenter(fragment.context, fragment)
    }
}