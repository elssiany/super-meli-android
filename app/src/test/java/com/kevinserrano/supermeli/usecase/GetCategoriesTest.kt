package com.kevinserrano.supermeli.usecase

import com.kevinserrano.supermeli.categoryEntity
import com.kevinserrano.supermeli.home.usecase.GetCategories
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
class GetCategoriesTest {

    private val meliRepository: MeliRepositoryImpl = mockk()
    private lateinit var getCategories: GetCategories

    @Before
    fun setUp() {
        getCategories = GetCategories(
            meliRepository = meliRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when invoke then it should return a Either Right returned by the repo`() = runTest {

        val expectResult = listOf(categoryEntity)
        coEvery { meliRepository.getCategories() } answers {
            expectResult
        }

        val result = getCategories.invoke()

        assert(result is Either.Right)
        Assert.assertNotNull((result as Either.Right).b)

        coVerify(exactly = 1) {
            meliRepository.getCategories()
        }
    }

    @Test
    fun `when invoke then it should return a Either Left returned by the repo`() = runTest {

        coEvery { meliRepository.getCategories() } answers {
            throw IllegalArgumentException(
                "Invalid argument"
            )
        }

        val result = getCategories.invoke()

        assert(result is Either.Left)
        Assert.assertNotNull((result as Either.Left).a)

        coVerify(exactly = 1) {
            meliRepository.getCategories()
        }
    }
}