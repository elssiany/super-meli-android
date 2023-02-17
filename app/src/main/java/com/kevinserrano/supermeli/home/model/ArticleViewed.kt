package com.kevinserrano.supermeli.home.model

import com.kevinserrano.supermeli.lib.meli.model.ArticleViewedEntity

data class ArticleViewed(
    val id: String,
    val title: String,
    val timestamp: Long,
    val price: String,
    val thumbnail: String,
    val freeShipping: Int,
    val originalPrice: String,
    val isMeliChoiceCandidate: Int,
    val sellerName: String,
    val sellerLink: String
) {
    fun toArticleViewedEntity() = ArticleViewedEntity(
        id = id,
        title = title,
        timestamp = timestamp,
        price = price,
        thumbnail = thumbnail,
        freeShipping = freeShipping,
        originalPrice = originalPrice,
        isMeliChoiceCandidate = isMeliChoiceCandidate,
        sellerLink = sellerLink,
        sellerName = sellerName
    )
}


