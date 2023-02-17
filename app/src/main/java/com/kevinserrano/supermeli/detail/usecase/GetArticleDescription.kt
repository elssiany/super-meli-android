package com.kevinserrano.supermeli.detail.usecase

import com.kevinserrano.supermeli.detail.model.ArticleDescription
import com.kevinserrano.supermeli.detail.model.ArticleDetail
import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import javax.inject.Inject

class GetArticleDescription @Inject constructor(
    private val meliRepository: MeliRepository
) {

    suspend operator fun invoke(articleId: String): ArticleDescription = try {
        meliRepository.getArticleDescription(articleId = articleId).toArticleDescription()
    } catch (ex: Exception) {
        ArticleDescription()
    }
}