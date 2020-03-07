package com.jan.products.di.component

import android.app.Application
import com.jan.products.base.BaseApplication
import com.jan.products.di.module.ActivityBindingModule
import com.jan.products.di.module.ApiModule
import com.jan.products.di.module.ContextModule
import com.jan.products.di.module.RoomModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [ContextModule::class, ApiModule::class, RoomModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: BaseApplication?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?

        fun build(): ApplicationComponent?
    }

}