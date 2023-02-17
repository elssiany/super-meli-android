package com.kevinserrano.supermeli.lib.meli.network

import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDescriptionResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDetailsResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiSearchListResponse
import com.kevinserrano.supermeli.search.ui.FILTER_ARTICLE_RELEVANCE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliService {

    @GET("sites/{siteId}/search")
    suspend fun searchArticle(
        @Path("siteId") siteId: String = SITE_COL,
        @Query("q")
        query: String,
        @Query("status")
        status: String = STATUS_ARTICLE_ACTIVE,
        @Query("shipping_cost")
        shippingCost: String? = null,
        @Query("sort")
        sort: String? = FILTER_ARTICLE_RELEVANCE,
        @Query("condition")
        condition: String? = null
    ): ApiSearchListResponse

    @GET("sites/{siteId}/search")
    suspend fun searchArticleByCategory(
        @Path("siteId") siteId: String = SITE_COL,
        @Query("category")
        category: String,
        @Query("shipping_cost")
        shippingCost: String? = null,
        @Query("sort")
        sort: String? = FILTER_ARTICLE_RELEVANCE,
        @Query("condition")
        condition: String? = null
    ): ApiSearchListResponse

    @GET("items/{articleId}")
    suspend fun getArticleDetails(
        @Path("articleId") articleId: String
    ): ApiArticleDetailsResponse

    @GET("items/{articleId}/description")
    suspend fun getArticleDescription(
        @Path("articleId") articleId: String
    ): ApiArticleDescriptionResponse
}

private const val SITE_COL = "MCO"
private const val STATUS_ARTICLE_ACTIVE = "active"
const val SHIPPING_COST = "free"
const val SORT_PRICE_ASC = "price_asc"
const val SORT_PRICE_DESC = "price_desc"
