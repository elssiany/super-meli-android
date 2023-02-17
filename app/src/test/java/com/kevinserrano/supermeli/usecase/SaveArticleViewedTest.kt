package com.kevinserrano.supermeli.usecase

import com.kevinserrano.supermeli.articleViewedEntity
import com.kevinserrano.supermeli.detail.usecase.SaveArticleViewed
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepositoryImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveArticleViewedTest {

    private val meliRepository: MeliRepositoryImpl = mockk(relaxed = true)
    private lateinit var saveArticleViewed: SaveArticleViewed

    @Before
    fun setUp() {
        saveArticleViewed = SaveArticleViewed(
            meliRepository = meliRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when invoke then it should save ArticleViewed in database from repo`() = runTest {

        val articleViewed = articleViewedEntity.toArticleViewed()

        coEvery { meliRepository.saveArticleViewed(articleViewed = articleViewedEntity) }.answers {  }

        saveArticleViewed.invoke(articleViewed = articleViewed)

        coVerify(exactly = 1) {
            meliRepository.saveArticleViewed(articleViewed = articleViewedEntity)
        }
    }
}