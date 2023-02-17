package com.kevinserrano.supermeli.usecase

import com.kevinserrano.supermeli.articleViewedEntity
import com.kevinserrano.supermeli.home.usecase.GetArticleVieweds
import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepositoryImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetArticleViewedsTest {

    private val meliRepository: MeliRepositoryImpl = mockk()
    private lateinit var getArticleVieweds: GetArticleVieweds

    @Before
    fun setUp() {
        getArticleVieweds = GetArticleVieweds(
            meliRepository = meliRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when invoke then it should return a Either Right returned by the repo`() = runTest {

        val expectResult = listOf(articleViewedEntity)
        coEvery { meliRepository.getArticleVieweds() } answers {
            flowOf(expectResult)
        }

        val result = getArticleVieweds.invoke()

        assert(result is Either.Right)
        Assert.assertNotNull((result as Either.Right).b)

        coVerify(exactly = 1) {
            meliRepository.getArticleVieweds()
        }
    }

    @Test
    fun `when invoke then it should return a Either Left returned by the repo`() = runTest {

        coEvery { meliRepository.getArticleVieweds() } answers {
            throw IllegalArgumentException(
                "Invalid argument"
            )
        }

        val result = getArticleVieweds.invoke()

        assert(result is Either.Left)
        Assert.assertNotNull((result as Either.Left).a)

        coVerify(exactly = 1) {
            meliRepository.getArticleVieweds()
        }
    }
}