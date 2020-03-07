package com.jan.products.di.module

import com.jan.products.ui.contact.ContactFragment
import com.jan.products.ui.products.ProductsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun provideProductsFragment(): ProductsFragment

    @ContributesAndroidInjector
    abstract fun provideContactFragment(): ContactFragment

}