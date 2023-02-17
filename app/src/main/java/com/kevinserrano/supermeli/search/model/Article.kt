package com.kevinserrano.supermeli.search.model

data class Article(
    val id: String,
    val title: String,
    val sellerName: String,
    val sellerLink: String,
    val price: String,
    val condition: String,
    val thumbnail: String,
    val isFreeShipping: Boolean,
    val meliChoiceCandidate: Boolean,
    val dueText: String
)


