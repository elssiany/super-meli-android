package com.kevinserrano.supermeli.search.ui

import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevinserrano.supermeli.lib.definitions.AbstractViewModel
import com.kevinserrano.supermeli.search.model.Article
import com.kevinserrano.supermeli.search.usecase.GetSearcResults
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val getSearcResults: GetSearcResults,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val firebaseAnalytics: FirebaseAnalytics
) : AbstractViewModel<SearchResultsViewModel.State, SearchResultsViewModel.Event>(
    initialState = State()
) {

    fun searchArticles(
        query: String,
        sort: String = FILTER_ARTICLE_RELEVANCE
    ) {
        updateState { it.copy(loading = true, error = false) }
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                delay(2000)//TODO esto no se debe hacer, solo es para ver la animacion del cargando mas tiempo
                getSearcResults(
                    query = query,
                    sort = sort
                ).either(
                    ::handleGetFailure, ::handleGetArticles
                )
            }
        }
    }

    fun filterSearchArticles(
        query: String,
        selectedTags: ArrayList<String>
    ) {
        updateState { it.copy(loading = true, error = false) }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(2000)//TODO esto no se debe hacer, solo es para ver la animacion del cargando mas tiempo
                getSearcResults(
                    query = query,
                    shippingCost = selectedTags.getShippingCostTag(),
                    sort = selectedTags.getSortTag(),
                    condition = selectedTags.getConditionTag()
                ).either(
                    ::handleGetFailure, ::handleGetArticles
                )
            }
        }
    }

    private fun ArrayList<String>.getShippingCostTag(): String? {
        return if(this.contains(FILTER_SHIPPING_FREE))
            FILTER_SHIPPING_FREE
        else
          null
    }

    private fun ArrayList<String>.getSortTag(): String {
        return if(this.contains(FILTER_PRICE_DESC))
            FILTER_PRICE_DESC
        else if(this.contains(FILTER_PRICE_ASC))
            FILTER_PRICE_ASC
        else
            FILTER_ARTICLE_RELEVANCE
    }

    private fun ArrayList<String>.getConditionTag(): String? {
        return if(this.contains(FILTER_ARTICLE_USED))
            FILTER_ARTICLE_USED
        else if(this.contains(FILTER_ARTICLE_NEW))
            FILTER_ARTICLE_NEW
        else
            null
    }

    private fun handleGetFailure(failure: Throwable) {
        firebaseCrashlytics.recordException(failure)
        updateState {
            it.copy(
                loading = false, error = failure.localizedMessage != null, articles = null
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

const val FILTER_ARTICLE_RELEVANCE = "relevance"
const val FILTER_PRICE_ASC = "price_asc"
const val FILTER_PRICE_DESC = "price_desc"
const val FILTER_ARTICLE_USED = "used"
const val FILTER_ARTICLE_NEW = "new"
const val FILTER_SHIPPING_FREE = "free"