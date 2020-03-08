package com.jan.products.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequest {

    @SerializedName("UserName")
    @Expose
    var userName: String = ""

    @SerializedName("Password")
    @Expose
    var password: String = ""
}