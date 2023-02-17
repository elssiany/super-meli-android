package com.kevinserrano.supermeli.lib.meli.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kevinserrano.supermeli.home.model.ArticleViewed

@Entity
data class ArticleViewedEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    @ColumnInfo(name = "freeShipping")
    val freeShipping: Int,
    @ColumnInfo(name = "isMeliChoiceCandidate")
    val isMeliChoiceCandidate: Int,
    @ColumnInfo(name = "originalPrice")
    val originalPrice: String,
    @ColumnInfo(name = "sellerName")
    val sellerName: String,
    @ColumnInfo(name = "sellerLink")
    val sellerLink: String
) {
    fun toArticleViewed() = ArticleViewed(
        id = id,
        title = title,
        timestamp = timestamp,
        price = price,
        thumbnail = thumbnail,
        freeShipping = freeShipping,
        originalPrice = originalPrice,
        isMeliChoiceCandidate = isMeliChoiceCandidate,
        sellerName = sellerName,
        sellerLink = sellerLink
    )
}


