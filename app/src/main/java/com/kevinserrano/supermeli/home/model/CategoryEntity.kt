package com.kevinserrano.supermeli.home.model


import com.google.gson.annotations.SerializedName

data class CategoryEntity(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("imageName")
    val imageName: String = "",
    @SerializedName("name")
    val name: String = ""
) {
    fun toCategory() = CategoryItem(
        id = id,
        name = name,
        imageName = imageName
    )
}