package com.kevinserrano.supermeli.home.usecase

import com.kevinserrano.supermeli.home.model.CategoryItem
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import com.kevinserrano.supermeli.lib.definitions.Either
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val meliRepository: MeliRepository
) {

    suspend operator fun invoke(): Either<Throwable, List<CategoryItem>> = try {
        val categories = meliRepository.getCategories().map { it.toCategory() }
        Either.Right(categories)
    } catch (ex: Exception) {
        Either.Left(ex)
    }
}