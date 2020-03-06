package com.jan.products.di.module

import com.jan.products.data.api.ProductsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class ApiModule {

    @Module
    companion object {

        private const val BASE_URL = "http://ws4.shareservice.co/TestMobile/rest/"

        @Singleton
        @Provides
        @JvmStatic
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideProductsApi(retrofit: Retrofit): ProductsApi {
            return retrofit.create(ProductsApi::class.java)
        }
    }

}