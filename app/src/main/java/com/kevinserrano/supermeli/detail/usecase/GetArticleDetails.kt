package com.kevinserrano.supermeli.detail.usecase

import com.kevinserrano.supermeli.detail.model.ArticleDetail
import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import javax.inject.Inject

class GetArticleDetails @Inject constructor(
    private val meliRepository: MeliRepository,
    private val getArticleDescription: GetArticleDescription
) {

    suspend operator fun invoke(articleId: String): Either<Throwable, ArticleDetail> = try {
        val articleDescription = getArticleDescription.invoke(articleId = articleId).plainText
        val articleDetail = meliRepository.getArticleDetail(articleId = articleId)
            .toArticleDetail(description = articleDescription)
        Either.Right(articleDetail)
    } catch (ex: Exception) {
        Either.Left(ex)
    }
}