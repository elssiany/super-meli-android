package com.kevinserrano.supermeli.usecase

import com.kevinserrano.supermeli.apiSearchListResponse
import com.kevinserrano.supermeli.categoryId
import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepositoryImpl
import com.kevinserrano.supermeli.searchbycategory.usecase.GetResultsByCategory
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
class GetResultsByCategoryTest {

    private val meliRepository: MeliRepositoryImpl = mockk()
    private lateinit var getResultsByCategory: GetResultsByCategory

    @Before
    fun setUp() {
        getResultsByCategory = GetResultsByCategory(
            meliRepository = meliRepository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when invoke then it should return a Either Right returned by the repo`() = runTest {

        coEvery { meliRepository.getResultsByCategory(categoryId = categoryId) } answers {
            apiSearchListResponse
        }

        val result = getResultsByCategory.invoke(categoryId = categoryId)

        assert(result is Either.Right)
        Assert.assertNotNull((result as Either.Right).b)

        coVerify(exactly = 1) {
            meliRepository.getResultsByCategory(categoryId = categoryId)
        }
    }

    @Test
    fun `when invoke then it should return a Either Left returned by the repo`() = runTest {

        coEvery { meliRepository.getResultsByCategory(categoryId = categoryId) } answers {
            throw IllegalArgumentException(
                "Invalid argument"
            )
        }

        val result = getResultsByCategory.invoke(categoryId = categoryId)

        assert(result is Either.Left)
        Assert.assertNotNull((result as Either.Left).a)

        coVerify(exactly = 1) {
            meliRepository.getResultsByCategory(categoryId = categoryId)
        }
    }
}