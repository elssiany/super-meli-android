package com.kevinserrano.supermeli.detail.usecase

import com.kevinserrano.supermeli.home.model.ArticleViewed
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import javax.inject.Inject

class SaveArticleViewed @Inject constructor(
    private val meliRepository: MeliRepository
) {

    suspend operator fun invoke(articleViewed: ArticleViewed) {
        meliRepository.saveArticleViewed(articleViewed = articleViewed.toArticleViewedEntity())
    }
}