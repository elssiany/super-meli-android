package com.kevinserrano.supermeli.search.usecase

import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import com.kevinserrano.supermeli.search.model.Article
import javax.inject.Inject

class GetSearcResults @Inject constructor(
    private val meliRepository: MeliRepository
) {

    suspend operator fun invoke(
        query: String,
        shippingCost: String? = null,
        sort: String? = null,
        condition: String? = null
    ): Either<Throwable, List<Article>> = try {
        val results = meliRepository.getResults(
            query = query,
            shippingCost = shippingCost, sort = sort, condition = condition
        ).results
        Either.Right(results.map { it.toArticle() })
    } catch (ex: Exception) {
        Either.Left(ex)
    }
}