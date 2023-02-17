package com.kevinserrano.supermeli

import com.kevinserrano.supermeli.home.model.CategoryEntity
import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDescriptionResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDetailsResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiSearchListResponse
import com.kevinserrano.supermeli.lib.meli.model.ArticleViewedEntity
import java.util.*

val articleId = "MCO802902"
val categoryId = "MCO1459"
val articleName = "Samsung 23 Ultra"
val description = "CAMARA DE RETROCESO WATERPROOF 8 LEDS.\n\nVISIÓN NOCTURNA, RESISTENTE AL AGUA , DE 720 X 480 PIXELES."
val apiArticleDescriptionResponse = ApiArticleDescriptionResponse(
    plainText = description
)
val apiArticleDetailsResponse = ApiArticleDetailsResponse(
    id = articleId,
    title = articleName,
    thumbnail = "https://http2.mlstatic.com/D_755864-MLA44156445075_112020-I.jpg",
    price = 900500.0,
    originalPrice = 900500.0,
    acceptsMercadopago = false
)
val apiSearchListResponse = ApiSearchListResponse(
    siteId = "MCO"
)
val categoryEntity = CategoryEntity(
    id = articleId,
    name = articleName,
    imageName = "home_mascotas"
)
val articleViewedEntity = ArticleViewedEntity(
    id = articleId,
    title = articleName,
    timestamp = Date().time,
    price = "$3'000.000",
    thumbnail = "https://http2.mlstatic.com/D_755864-MLA44156445075_112020-I.jpg",
    freeShipping = 1,
    isMeliChoiceCandidate = 1,
    originalPrice = "$3'000.000",
    sellerName = "Doralda",
    sellerLink = "http://http2.mlstatic.com/D_755864-MLA44156445075_112020-I.jpg"
)

val sellerName = "Neyder San"
val sellerLink = "http://http2.mlstatic.com/D_755864-MLA44156445075_112020-I.jpg"