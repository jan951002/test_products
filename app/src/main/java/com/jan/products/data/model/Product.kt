package com.jan.products.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {

    @SerializedName("Description")
    @Expose
    var description: String = ""

    @SerializedName("ImageUrl")
    @Expose
    var imageUrl: String = ""

    @SerializedName("Name")
    @Expose
    var name: String = ""
}