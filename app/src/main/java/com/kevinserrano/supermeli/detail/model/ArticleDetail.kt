package com.kevinserrano.supermeli.detail.model

data class ArticleDetail(
    val id: String,
    val title: String,
    val price: String,
    val description: String,
    val originalPrice: String,
    val characteristic: String,
    val condition: String,
    val thumbnail: String,
    val pictures: List<String> = listOf(),
    val isFreeShipping: Boolean,
    val permalink: String,
    val availableQuantity: Int = 0
)


