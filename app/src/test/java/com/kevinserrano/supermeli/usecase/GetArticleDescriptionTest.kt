package com.kevinserrano.supermeli.usecase

import com.kevinserrano.supermeli.apiArticleDescriptionResponse
import com.kevinserrano.supermeli.articleId
import com.kevinserrano.supermeli.detail.usecase.GetArticleDescription
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
class GetArticleDescriptionTest {

    private val meliRepository: MeliRepositoryImpl = mockk()
    private lateinit var getArticleDescription: GetArticleDescription

    @Before
    fun setUp() {
        getArticleDescription = GetArticleDescription(
            meliRepository = meliRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when invoke then it should return a ArticleDescription returned by the repo`() = runTest {

        coEvery { meliRepository.getArticleDescription(articleId = articleId) } answers {
            apiArticleDescriptionResponse
        }

        val result = getArticleDescription.invoke(articleId = articleId)

        assert(result.plainText.isNotEmpty())

        coVerify(exactly = 1) {
            meliRepository.getArticleDescription(articleId = articleId)
        }
    }

    @Test
    fun `when invoke then it should return a exception returned by the repo`() = runTest {

        coEvery { meliRepository.getArticleDescription(articleId = articleId) } answers {
            throw IllegalArgumentException(
                "Invalid argument"
            )
        }

        val result = getArticleDescription.invoke(articleId = articleId)

        assert(result.plainText.isEmpty())

        coVerify(exactly = 1) {
            meliRepository.getArticleDescription(articleId = articleId)
        }
    }
}