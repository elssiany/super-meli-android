package com.kevinserrano.supermeli.home.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kevinserrano.supermeli.home.model.ArticleViewed
import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class GetArticleVieweds @Inject constructor(
    private val meliRepository: MeliRepository
) {

    operator fun invoke(): Either<Throwable, LiveData<List<ArticleViewed>>> = try {
        val articleVieweds = meliRepository.getArticleVieweds()
            .map { it.map { articleViewed -> articleViewed.toArticleViewed() } }.asLiveData()
        Either.Right(articleVieweds)
    } catch (ex: Exception) {
        Either.Left(ex)
    }
}