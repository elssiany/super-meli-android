package com.kevinserrano.supermeli.home.model


import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("imageName")
    val imageName: String = "",
    @SerializedName("name")
    val name: String = ""
)