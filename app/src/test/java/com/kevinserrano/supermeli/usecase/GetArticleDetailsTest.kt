package com.kevinserrano.supermeli.usecase

import com.kevinserrano.supermeli.apiArticleDescriptionResponse
import com.kevinserrano.supermeli.apiArticleDetailsResponse
import com.kevinserrano.supermeli.articleId
import com.kevinserrano.supermeli.detail.usecase.GetArticleDescription
import com.kevinserrano.supermeli.detail.usecase.GetArticleDetails
import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepositoryImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetArticleDetailsTest {

    private val meliRepository: MeliRepositoryImpl = mockk()
    private lateinit var getArticleDetails: GetArticleDetails
    private lateinit var getArticleDescription: GetArticleDescription

    @Before
    fun setUp() {
        getArticleDescription = GetArticleDescription(
            meliRepository = meliRepository
        )
        getArticleDetails = GetArticleDetails(
            meliRepository = meliRepository,
            getArticleDescription = getArticleDescription
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `give GetArticleDetails when invoke then it should return a Either Right returned by the repo`() = runTest {

        coEvery { meliRepository.getArticleDetail(articleId = articleId) } answers {
            apiArticleDetailsResponse
        }

        coEvery { meliRepository.getArticleDescription(articleId = articleId) } answers {
            apiArticleDescriptionResponse
        }

        val result = getArticleDetails.invoke(articleId = articleId)

        assert(result is Either.Right)
        Assert.assertNotNull((result as Either.Right).b)

        coVerify(exactly = 1) {
            meliRepository.getArticleDetail(articleId = articleId)
            meliRepository.getArticleDescription(articleId = articleId)
        }
    }

    @Test
    fun `give GetArticleDetails when invoke then it should return a Either Left returned by the repo`() = runTest {

        coEvery { meliRepository.getArticleDetail(articleId = articleId) } answers {
            throw IllegalArgumentException(
                "Invalid argument"
            )
        }

        coEvery { meliRepository.getArticleDescription(articleId = articleId) } answers {
            throw IllegalArgumentException(
                "Invalid argument"
            )
        }

        val result = getArticleDetails.invoke(articleId = articleId)

        assert(result is Either.Left)
        Assert.assertNotNull((result as Either.Left).a)

        coVerify(exactly = 1) {
            meliRepository.getArticleDetail(articleId = articleId)
            meliRepository.getArticleDescription(articleId = articleId)
        }
    }
}