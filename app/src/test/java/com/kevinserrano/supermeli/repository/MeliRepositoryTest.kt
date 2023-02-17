package com.kevinserrano.supermeli.repository

import android.content.Context
import com.google.gson.Gson
import com.kevinserrano.supermeli.apiArticleDescriptionResponse
import com.kevinserrano.supermeli.apiArticleDetailsResponse
import com.kevinserrano.supermeli.apiSearchListResponse
import com.kevinserrano.supermeli.articleId
import com.kevinserrano.supermeli.articleName
import com.kevinserrano.supermeli.articleViewedEntity
import com.kevinserrano.supermeli.categoryId
import com.kevinserrano.supermeli.lib.meli.local.ArticleViewedDao
import com.kevinserrano.supermeli.lib.meli.network.MeliService
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepositoryImpl
import com.kevinserrano.supermeli.search.ui.FILTER_ARTICLE_RELEVANCE
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MeliRepositoryTest {

    private val context: Context = mockk(relaxed = true)
    private val meliService: MeliService = mockk()
    private val gson = Gson()
    private lateinit var repository: MeliRepository
    private val articleViewedDao: ArticleViewedDao = mockk(relaxed = true)

    @Before
    fun setUp() {
        repository = MeliRepositoryImpl(
            gson = gson,
            context = context,
            articleViewedDao = articleViewedDao,
            meliService = meliService
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when getArticleVieweds called then it should return a flow with what is in the database`() =
        runTest {
            coEvery { articleViewedDao.getLastViewedItems() } answers {
                flowOf(
                    listOf(
                        articleViewedEntity
                    )
                )
            }

            val result = repository.getArticleVieweds().first()
            val articleViewedEntityResult = result.first()

            coVerify(exactly = 1) {
                articleViewedDao.getLastViewedItems()
            }
            Assert.assertTrue(result.isNotEmpty())
            Assert.assertTrue(articleViewedEntity == articleViewedEntityResult)
        }

    @Test
    fun `when getArticleVieweds called then it should return a flow with list empty what is in the database`() =
        runTest {
            coEvery { articleViewedDao.getLastViewedItems() } answers { flowOf(listOf()) }

            val result = repository.getArticleVieweds().first()
            coVerify(exactly = 1) {
                articleViewedDao.getLastViewedItems()
            }
            Assert.assertTrue(result.isEmpty())
        }

    @Test
    fun `when getResultsByCategory called then it should return value ApiSearchListResponse`() =
        runTest {
            coEvery { meliService.searchArticleByCategory(category = categoryId) } answers { apiSearchListResponse }

            val result = repository.getResultsByCategory(categoryId = categoryId)
            coVerify(exactly = 1) {
                meliService.searchArticleByCategory(category = categoryId)
            }
            Assert.assertTrue(result.siteId == "MCO")
        }

    @Test
    fun `when getResultsByCategory called then it should return value exception`() = runTest {
        coEvery { meliService.searchArticleByCategory(category = categoryId) } answers {
            throw ClassCastException(
                "Invalid argument"
            )
        }
        try {
            repository.getResultsByCategory(categoryId = categoryId)
        } catch (e: java.lang.Exception) {
            Assert.assertTrue(e is ClassCastException)
        }
        coVerify(exactly = 1) {
            meliService.searchArticleByCategory(category = categoryId)
        }
    }

    @Test
    fun `when getResults called then it should return value ApiSearchListResponse`() =
        runTest {
            val query = "samsung"
            val sort = FILTER_ARTICLE_RELEVANCE
            coEvery {
                meliService.searchArticle(
                    query = query,
                    sort = sort
                )
            } answers { apiSearchListResponse }

            val result = repository.getResults(
                query = query, sort = sort, shippingCost = null,
                condition = null
            )
            coVerify(exactly = 1) {
                meliService.searchArticle(
                    query = query,
                    sort = sort
                )
            }
            Assert.assertTrue(result.siteId == "MCO")
        }

    @Test
    fun `when getResults called then it should return value exception`() = runTest {
        val query = "samsung"
        val sort = FILTER_ARTICLE_RELEVANCE
        coEvery {
            meliService.searchArticle(
                query = query,
                sort = sort
            )
        } answers {
            throw ClassCastException(
                "Invalid argument"
            )
        }
        try {
            repository.getResults(
                query = query, sort = sort, shippingCost = null,
                condition = null
            )
        } catch (e: java.lang.Exception) {
            Assert.assertTrue(e is ClassCastException)
        }
        coVerify(exactly = 1) {
            meliService.searchArticle(
                query = query,
                sort = sort
            )
        }
    }

    @Test
    fun `when getArticleDetail called then it should return value ApiArticleDetailsResponse`() =
        runTest {
            coEvery {
                meliService.getArticleDetails(articleId = articleId)
            } answers { apiArticleDetailsResponse }

            val result = repository.getArticleDetail(
                articleId = articleId
            )
            coVerify(exactly = 1) {
                meliService.getArticleDetails(articleId = articleId)
            }
            Assert.assertTrue(result.id == apiArticleDetailsResponse.id)
            Assert.assertTrue(result.title == articleName)
            Assert.assertTrue(result.price == apiArticleDetailsResponse.price)
            Assert.assertTrue(result.originalPrice == apiArticleDetailsResponse.originalPrice)
            Assert.assertTrue(result.acceptsMercadopago == apiArticleDetailsResponse.acceptsMercadopago)
        }

    @Test
    fun `when getArticleDetail called then it should return value exception`() = runTest {

        coEvery {
            meliService.getArticleDetails(articleId = articleId)
        } answers {
            throw ClassCastException(
                "Invalid argument"
            )
        }
        try {
            repository.getArticleDetail(
                articleId = articleId
            )
        } catch (e: java.lang.Exception) {
            Assert.assertTrue(e is ClassCastException)
        }
        coVerify(exactly = 1) {
            meliService.getArticleDetails(articleId = articleId)
        }
    }

    @Test
    fun `when getArticleDescription called then it should return value ApiArticleDescriptionResponse`() =
        runTest {
            coEvery {
                meliService.getArticleDescription(articleId = articleId)
            } answers { apiArticleDescriptionResponse }

            val result = repository.getArticleDescription(
                articleId = articleId
            )
            coVerify(exactly = 1) {
                meliService.getArticleDescription(articleId = articleId)
            }
            Assert.assertTrue(result.plainText == apiArticleDescriptionResponse.plainText)
        }

    @Test
    fun `when saveArticleViewed called then it should save value ArticleViewedEntity`() = runTest {
        val expectedResult = "Apple 14"
        repository.saveArticleViewed(articleViewedEntity.copy(title = expectedResult))
        repository.getArticleVieweds().collect {
            val result = it.last()
            Assert.assertTrue(result.title == expectedResult)
            Assert.assertTrue(articleViewedEntity.title != result.title)
        }
    }
}