package com.kevinserrano.supermeli.searchbycategory.ui

import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevinserrano.supermeli.lib.definitions.AbstractViewModel
import com.kevinserrano.supermeli.search.model.Article
import com.kevinserrano.supermeli.searchbycategory.usecase.GetResultsByCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class SearchByCategoryViewModel @Inject constructor(
    private val getResultsByCategory: GetResultsByCategory,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val firebaseAnalytics: FirebaseAnalytics
) : AbstractViewModel<SearchByCategoryViewModel.State, SearchByCategoryViewModel.Event>(
    initialState = State()
) {

    fun searchArticlesByCategory(categoryId: String) {
        updateState { it.copy(loading = true, error = false) }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(2000)//TODO esto no se debe hacer, solo es para ver la animacion del cargando mas tiempo
                getResultsByCategory(categoryId).either(
                    ::handleGetFailure,
                    ::handleGetArticles
                )
            }
        }
    }

    private fun handleGetFailure(failure: Throwable) {
        firebaseCrashlytics.recordException(failure)
        updateState {
            it.copy(
                loading = false, error = failure.localizedMessage != null,
                articles = null
            )
        }
    }

    private fun handleGetArticles(articles: List<Article>) {
        updateState { it.copy(loading = false, articles = articles) }
    }

    fun onArticleItemClicked(article: Article) {
        firebaseAnalytics.logEvent("btn_article_item", null)
        _event.value = Event.NavigationToArticleDetail(
            articleId = article.id,
            isMeliChoiceCandidate = article.meliChoiceCandidate,
            sellerName = article.sellerName,
            sellerLink = article.sellerLink
        )
    }

    fun eventNone() {
        _event.value = Event.None
    }

    data class State(
        val loading: Boolean = false,
        val error: Boolean = false,
        val articles: List<Article>? = null
    )

    sealed class Event {
        data class NavigationToArticleDetail(
            val articleId: String,
            val sellerName: String,
            val isMeliChoiceCandidate: Boolean,
            val sellerLink: String
        ) : Event()

        object None : Event()
    }
}