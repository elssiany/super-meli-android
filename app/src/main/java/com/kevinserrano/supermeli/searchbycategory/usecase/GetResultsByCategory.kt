package com.kevinserrano.supermeli.searchbycategory.usecase

import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import com.kevinserrano.supermeli.search.model.Article
import javax.inject.Inject

class GetResultsByCategory @Inject constructor(
    private val meliRepository: MeliRepository
) {

    suspend operator fun invoke(categoryId: String): Either<Throwable, List<Article>> = try {
        val results = meliRepository.getResultsByCategory(categoryId = categoryId).results
        Either.Right(results.map { it.toArticle() })
    } catch (ex: Exception) {
        Either.Left(ex)
    }
}