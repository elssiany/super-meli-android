package com.kevinserrano.supermeli.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevinserrano.supermeli.home.model.ArticleViewed
import com.kevinserrano.supermeli.home.model.CategoryItem
import com.kevinserrano.supermeli.home.usecase.GetArticleVieweds
import com.kevinserrano.supermeli.home.usecase.GetCategories
import com.kevinserrano.supermeli.lib.definitions.AbstractViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategories: GetCategories,
    private val getArticleVieweds: GetArticleVieweds,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val firebaseAnalytics: FirebaseAnalytics
) : AbstractViewModel<HomeViewModel.State, HomeViewModel.Event>(
    initialState = State()
) {

    init {
        fetchCategories()
        fetchArticleVieweds()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getCategories().either(
                    ::handleGetFailure,
                    ::handleGetCategories
                )
            }
        }
    }

    private fun fetchArticleVieweds() {
        getArticleVieweds.invoke().either(
            ::handleGetFailure,
            ::handleGetArticleVieweds
        )
    }

    private fun handleGetFailure(failure: Throwable) {
        firebaseCrashlytics.recordException(failure)
        updateState { it.copy(error = failure.localizedMessage != null) }
    }

    private fun handleGetCategories(categories: List<CategoryItem>) {
        updateState { it.copy(categories = categories) }
    }

    private fun handleGetArticleVieweds(articleVieweds: LiveData<List<ArticleViewed>>) {
        articleVieweds.observeForever {
            updateState { currentState -> currentState.copy(articleVieweds = it) }
        }
    }

    fun onCategoryItemClicked(title: String, categoryId: String) {
        firebaseAnalytics.logEvent("btn_category_item", null)
        _event.value = Event.NavigationToSearchByCategory(title = title, categoryId = categoryId)
    }

    fun onArticleViewedClick(
        articleViewedId: String,
        isMeliChoiceCandidate: Boolean,
        sellerName: String,
        sellerLink: String
    ) {
        firebaseAnalytics.logEvent("btn_article_viewed_item", null)
        _event.value = Event.NavigationToArticleDetail(
            articleViewedId = articleViewedId,
            isMeliChoiceCandidate = isMeliChoiceCandidate,
            sellerName = sellerName,
            sellerLink = sellerLink
        )
    }

    data class State(
        val error: Boolean = false,
        val categories: List<CategoryItem>? = null,
        val articleVieweds: List<ArticleViewed>? = null
    )

    sealed class Event {
        data class NavigationToSearchByCategory(val title: String, val categoryId: String) : Event()
        data class NavigationToArticleDetail(
            val articleViewedId: String,
            val isMeliChoiceCandidate: Boolean,
            val sellerName: String,
            val sellerLink: String
        ) : Event()
    }
}