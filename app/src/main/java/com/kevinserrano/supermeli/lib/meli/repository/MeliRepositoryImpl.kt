package com.kevinserrano.supermeli.lib.meli.repository

import android.content.Context
import com.google.gson.Gson
import com.kevinserrano.supermeli.home.model.CategoryEntity
import com.kevinserrano.supermeli.lib.meli.local.ArticleViewedDao
import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDescriptionResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDetailsResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiSearchListResponse
import com.kevinserrano.supermeli.lib.meli.model.ArticleViewedEntity
import com.kevinserrano.supermeli.lib.meli.network.MeliService
import com.kevinserrano.supermeli.utils.getDataFromAssets
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MeliRepositoryImpl @Inject constructor(
    private val gson: Gson,
    @ApplicationContext private val context: Context,
    private val articleViewedDao: ArticleViewedDao,
    private val meliService: MeliService,
) : MeliRepository {

    override suspend fun getCategories(): List<CategoryEntity> {
        val categoriesJson = context.getDataFromAssets(fileName = CATEGORY_JSON_NAME)
        return gson.fromJson(categoriesJson, Array<CategoryEntity>::class.java).toList()
    }

    override fun getArticleVieweds() = articleViewedDao.getLastViewedItems()

    override suspend fun getResultsByCategory(categoryId: String): ApiSearchListResponse {
        return meliService.searchArticleByCategory(category = categoryId)
    }

    override suspend fun getResults(
        query: String,
        shippingCost: String?,
        sort: String?,
        condition: String?
    ): ApiSearchListResponse {
        return meliService.searchArticle(
            query = query,
            shippingCost = shippingCost,
            sort = sort,
            condition = condition
        )
    }

    override suspend fun getArticleDetail(articleId: String): ApiArticleDetailsResponse {
        return meliService.getArticleDetails(articleId = articleId)
    }

    override suspend fun getArticleDescription(articleId: String): ApiArticleDescriptionResponse {
        return meliService.getArticleDescription(articleId = articleId)
    }

    override suspend fun saveArticleViewed(articleViewed: ArticleViewedEntity) {
        articleViewedDao.insert(articleViewed)
    }
}

private const val CATEGORY_JSON_NAME = "categories.json"
