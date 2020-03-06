package com.jan.products.data.api

import com.jan.products.data.model.Product
import io.reactivex.Single
import retrofit2.http.POST

interface ProductsApi {

    @POST("GetProductsData")
    fun getProducts(): Single<ArrayList<Product>>

}