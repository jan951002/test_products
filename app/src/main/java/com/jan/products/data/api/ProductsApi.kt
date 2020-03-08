package com.jan.products.data.api

import com.jan.products.data.model.ExpirationDate
import com.jan.products.data.model.Product
import com.jan.products.data.request.LoginRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductsApi {

    @POST("GetProductsData")
    fun getProducts(): Single<ArrayList<Product>>

    @POST("Login")
    fun login(@Body loginRequest: LoginRequest): Single<ExpirationDate>
}