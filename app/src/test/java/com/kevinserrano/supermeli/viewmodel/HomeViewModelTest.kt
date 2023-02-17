package com.kevinserrano.supermeli.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevinserrano.supermeli.articleId
import com.kevinserrano.supermeli.categoryId
import com.kevinserrano.supermeli.home.ui.HomeViewModel
import com.kevinserrano.supermeli.home.usecase.GetArticleVieweds
import com.kevinserrano.supermeli.home.usecase.GetCategories
import com.kevinserrano.supermeli.sellerLink
import com.kevinserrano.supermeli.sellerName
import com.kevinserrano.supermeli.util.MainDispatcherRule
import io.mockk.mockk
import io.mockk.verify
import java.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    private val getArticleVieweds: GetArticleVieweds = mockk(relaxed = true)
    private val getCategories: GetCategories = mockk(relaxed = true)
    private val firebaseCrashlytics: FirebaseCrashlytics = mockk(relaxed = true)
    private val firebaseAnalytics: FirebaseAnalytics = mockk(relaxed = true)
    private lateinit var homeViewModel: HomeViewModel
    private val observerEvents: Observer<HomeViewModel.Event> = mockk(relaxed = true)

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            getCategories = getCategories,
            getArticleVieweds = getArticleVieweds,
            firebaseAnalytics = firebaseAnalytics,
            firebaseCrashlytics = firebaseCrashlytics,
        )
    }

    @Test
    fun `when onArticleViewedClick then should return an expected emit event NavigationToArticleDetail`() =
        runTest {

            val slots = mutableListOf<HomeViewModel.Event>()


            homeViewModel.onArticleViewedClick(
                articleViewedId = articleId,
                isMeliChoiceCandidate = false,
                sellerName = sellerName,
                sellerLink = sellerLink
            )
            homeViewModel.event.observeForever(observerEvents)
            verify { observerEvents.onChanged(capture(slots)) }
            Assert.assertTrue(slots.first() is HomeViewModel.Event.NavigationToArticleDetail)
        }

    @Test
    fun `when onCategoryItemClicked then should return an expected emit event NavigationToSearchByCategory`() =
        runTest {

            val slots = mutableListOf<HomeViewModel.Event>()

            homeViewModel.onCategoryItemClicked(
                title = "Samsung s23",
                categoryId = categoryId
            )
            homeViewModel.event.observeForever(observerEvents)
            verify { observerEvents.onChanged(capture(slots)) }
            Assert.assertTrue(slots.first() is HomeViewModel.Event.NavigationToSearchByCategory)
        }
}