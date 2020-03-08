package com.jan.products.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ExpirationDate {

    @SerializedName("ExpirationDate")
    @Expose
    var expirationDate: String = ""
}