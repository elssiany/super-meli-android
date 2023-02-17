package com.kevinserrano.supermeli.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevinserrano.supermeli.apiArticleDetailsResponse
import com.kevinserrano.supermeli.articleId
import com.kevinserrano.supermeli.detail.ui.ArticleDetailViewModel
import com.kevinserrano.supermeli.detail.usecase.GetArticleDetails
import com.kevinserrano.supermeli.detail.usecase.SaveArticleViewed
import com.kevinserrano.supermeli.lib.definitions.Either
import com.kevinserrano.supermeli.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import java.io.InvalidObjectException
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleDetailViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    private val getArticleDetails: GetArticleDetails = mockk(relaxed = true)
    private val saveArticleViewed: SaveArticleViewed = mockk(relaxed = true)
    private val firebaseCrashlytics: FirebaseCrashlytics = mockk(relaxed = true)
    private val firebaseAnalytics: FirebaseAnalytics = mockk(relaxed = true)
    private lateinit var articleDetailViewModel: ArticleDetailViewModel
    private val observerStates: Observer<ArticleDetailViewModel.State> = mockk(relaxed = true)
    private val observerEvents: Observer<ArticleDetailViewModel.Event> = mockk(relaxed = true)
    private val description = "Camara X3 de 150 megas de alta calidad"
    private val articleDetails =
        apiArticleDetailsResponse.toArticleDetail(description = description)

    @Before
    fun setUp() {
        articleDetailViewModel = ArticleDetailViewModel(
            getArticleDetails = getArticleDetails,
            saveArticleViewed = saveArticleViewed,
            firebaseAnalytics = firebaseAnalytics,
            firebaseCrashlytics = firebaseCrashlytics,
        )
        Dispatchers.setMain(Dispatchers.IO)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchArticleDetails then should return an expected success article details`() = runTest {

        val slots = mutableListOf<ArticleDetailViewModel.State>()
        val expectResult = ArticleDetailViewModel.State(
            loading = false,
            error = false,
            articleDetail = articleDetails
        )

        coEvery { getArticleDetails(articleId = articleId) } returns Either.Right(articleDetails)
        articleDetailViewModel.fetchArticleDetails(articleId = articleId)
        articleDetailViewModel.state.observeForever(observerStates)
        runBlocking { delay(6000) }
        verify { observerStates.onChanged(capture(slots)) }
        Assert.assertFalse(slots.first().error)
        Assert.assertTrue(slots.first().loading)
        Assert.assertTrue(slots.first().articleDetail == null)
        Assert.assertFalse(slots.last().error)
        Assert.assertFalse(slots.last().loading)
        Assert.assertTrue(slots.last() == expectResult)
    }

    @Test
    fun `when fetchArticleDetails then should return an expected failed value`() = runTest {
        val slots = mutableListOf<ArticleDetailViewModel.State>()
        val expectResult = InvalidObjectException("Error datas")

        coEvery { getArticleDetails(articleId = articleId) } returns Either.Left(expectResult)
        articleDetailViewModel.fetchArticleDetails(articleId = articleId)
        articleDetailViewModel.state.observeForever(observerStates)
        runBlocking { delay(6000) }
        verify { observerStates.onChanged(capture(slots)) }
        Assert.assertFalse(slots.first().error)
        Assert.assertTrue(slots.first().loading)
        Assert.assertTrue(slots.first().articleDetail == null)
        Assert.assertTrue(slots.last().error)
        Assert.assertFalse(slots.last().loading)
        Assert.assertTrue(slots.last().articleDetail == null)
    }

    @Test
    fun `when goSaveArticleViewed then should save ArticleDetail`() = runTest {
        val sellerName = "Neyder San"
        val sellerLink = "http://http2.mlstatic.com/D_755864-MLA44156445075_112020-I.jpg"
        articleDetailViewModel.goSaveArticleViewed(
            articleDetail = articleDetails,
            isMeliChoiceCandidate = true,
            sellerName = sellerName,
            sellerLink = sellerLink
        )
        coVerify(exactly = 1) {
            saveArticleViewed.invoke(articleViewed = any())
        }
    }

    @Test
    fun `when eventNone called then should return an expected event None value`() = runTest {

        val slots = mutableListOf<ArticleDetailViewModel.Event>()
        articleDetailViewModel.eventNone()
        articleDetailViewModel.event.observeForever(observerEvents)
        verify { observerEvents.onChanged(capture(slots)) }
        Assert.assertFalse(slots.first() == ArticleDetailViewModel.Event.NavigationToBuy(articleLink = articleDetails.permalink))
        Assert.assertTrue(slots.first() == ArticleDetailViewModel.Event.None)
    }
}