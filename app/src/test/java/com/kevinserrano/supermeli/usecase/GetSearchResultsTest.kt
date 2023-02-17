package com.kevinserrano.supermeli.usecase

import com.kevinserrano.supermeli.apiSearchListResponse
import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepositoryImpl
import com.kevinserrano.supermeli.search.ui.FILTER_ARTICLE_NEW
import com.kevinserrano.supermeli.search.ui.FILTER_ARTICLE_RELEVANCE
import com.kevinserrano.supermeli.search.usecase.GetSearcResults
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
class GetSearchResultsTest {

    private val meliRepository: MeliRepositoryImpl = mockk()
    private lateinit var getSearcResults: GetSearcResults

    @Before
    fun setUp() {
        getSearcResults = GetSearcResults(
            meliRepository = meliRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when invoke then it should return a Either Right returned by the repo`() = runTest {
        val query = "samsung"
        coEvery { meliRepository.getResults(query = query,
        shippingCost = "free",
            sort = FILTER_ARTICLE_RELEVANCE,
            condition = FILTER_ARTICLE_NEW) } answers {
            apiSearchListResponse
        }

        val result = getSearcResults.invoke(query = query,
            shippingCost = "free",
            sort = FILTER_ARTICLE_RELEVANCE,
            condition = FILTER_ARTICLE_NEW)

        assert(result is Either.Right)
        Assert.assertNotNull((result as Either.Right).b)

        coVerify(exactly = 1) {
            meliRepository.getResults(query = query,
                shippingCost = "free",
                sort = FILTER_ARTICLE_RELEVANCE,
                condition = FILTER_ARTICLE_NEW)
        }
    }

    @Test
    fun `when invoke then it should return a Either Left returned by the repo`() = runTest {
        val query = "samsung"
        coEvery { meliRepository.getResults(query = query,
            shippingCost = "free",
            sort = FILTER_ARTICLE_RELEVANCE,
            condition = FILTER_ARTICLE_NEW) } answers {
            throw IllegalArgumentException(
                "Invalid argument"
            )
        }

        val result = getSearcResults.invoke(query = query,
            shippingCost = "free",
            sort = FILTER_ARTICLE_RELEVANCE,
            condition = FILTER_ARTICLE_NEW)

        assert(result is Either.Left)
        Assert.assertNotNull((result as Either.Left).a)

        coVerify(exactly = 1) {
            meliRepository.getResults(query = query,
                shippingCost = "free",
                sort = FILTER_ARTICLE_RELEVANCE,
                condition = FILTER_ARTICLE_NEW)
        }
    }
}