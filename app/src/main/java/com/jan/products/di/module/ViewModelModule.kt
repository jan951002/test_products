package com.jan.products.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jan.products.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    /*
    @Binds
    @IntoMap
    @ViewModelKey(DogsViewModel::class)
    abstract fun bindDogstViewModel(dogsViewModel: DogsViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(CreateDogViewModel::class)
    abstract fun bindCreateDogViewModel(createDogViewModel: CreateDogViewModel?): ViewModel?

    */

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?
}