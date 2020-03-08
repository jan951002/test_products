package com.jan.products.di.module

import com.jan.products.ui.main.MainActivity
import com.jan.products.ui.login.LoginActivity
import com.jan.products.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainFragmentBindingModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainFragmentBindingModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainFragmentBindingModule::class])
    abstract fun bindLoginActivity(): LoginActivity
}