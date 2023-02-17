package com.kevinserrano.supermeli.lib.meli.model


import com.google.gson.annotations.SerializedName
import com.kevinserrano.supermeli.detail.model.ArticleDescription

data class ApiArticleDescriptionResponse(
    @SerializedName("date_created")
    val dateCreated: String = "",
    @SerializedName("last_updated")
    val lastUpdated: String = "",
    @SerializedName("plain_text")
    val plainText: String = "",
    @SerializedName("snapshot")
    val snapshot: Snapshot = Snapshot(),
    @SerializedName("text")
    val text: String = ""
) {
    data class Snapshot(
        @SerializedName("height")
        val height: Int = 0,
        @SerializedName("status")
        val status: String = "",
        @SerializedName("url")
        val url: String = "",
        @SerializedName("width")
        val width: Int = 0
    )

    fun toArticleDescription() = ArticleDescription(
        text = text,
        plainText = plainText
    )
}