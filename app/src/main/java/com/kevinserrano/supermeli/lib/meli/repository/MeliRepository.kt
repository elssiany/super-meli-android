package com.kevinserrano.supermeli.lib.meli.repository

import com.kevinserrano.supermeli.home.model.CategoryEntity
import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDescriptionResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDetailsResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiSearchListResponse
import com.kevinserrano.supermeli.lib.meli.model.ArticleViewedEntity
import kotlinx.coroutines.flow.Flow

interface MeliRepository {

    suspend fun getCategories(): List<CategoryEntity>

    fun getArticleVieweds(): Flow<List<ArticleViewedEntity>>

    suspend fun getResultsByCategory(categoryId: String): ApiSearchListResponse

    suspend fun getResults(
        query: String,
        shippingCost: String?,
        sort: String?,
        condition: String?
    ): ApiSearchListResponse

    suspend fun getArticleDetail(articleId: String): ApiArticleDetailsResponse

    suspend fun getArticleDescription(articleId: String): ApiArticleDescriptionResponse

    suspend fun saveArticleViewed(articleViewed: ArticleViewedEntity)
}