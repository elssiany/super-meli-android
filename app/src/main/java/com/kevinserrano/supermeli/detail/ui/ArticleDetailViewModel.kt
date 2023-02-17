package com.kevinserrano.supermeli.detail.ui

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevinserrano.supermeli.detail.model.ArticleDetail
import com.kevinserrano.supermeli.detail.usecase.GetArticleDetails
import com.kevinserrano.supermeli.detail.usecase.SaveArticleViewed
import com.kevinserrano.supermeli.home.model.ArticleViewed
import com.kevinserrano.supermeli.lib.definitions.AbstractViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticleDetails: GetArticleDetails,
    private val saveArticleViewed: SaveArticleViewed,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val firebaseAnalytics: FirebaseAnalytics
) : AbstractViewModel<ArticleDetailViewModel.State, ArticleDetailViewModel.Event>(
    initialState = State()
) {

    fun fetchArticleDetails(articleId: String) {
        updateState { it.copy(loading = true, error = false) }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(2000)//TODO esto no se debe hacer, solo es para ver la animacion del cargando mas tiempo
                getArticleDetails(articleId).either(
                    ::handleGetFailure,
                    ::handleGetArticleDetails
                )
            }
        }
    }

    private fun handleGetFailure(failure: Throwable) {
        firebaseCrashlytics.recordException(failure)
        updateState {
            it.copy(
                loading = false, error = failure.localizedMessage != null,
                articleDetail = null
            )
        }
    }

    private fun handleGetArticleDetails(articleDetail: ArticleDetail) {
        updateState { it.copy(loading = false, articleDetail = articleDetail) }
    }

    fun goSaveArticleViewed(
        articleDetail: ArticleDetail,
        isMeliChoiceCandidate: Boolean,
        sellerName: String,
        sellerLink: String
    ) {
        viewModelScope.launch {
            saveArticleViewed.invoke(
                articleViewed = ArticleViewed(
                    id = articleDetail.id,
                    title = articleDetail.title,
                    originalPrice = articleDetail.originalPrice,
                    price = articleDetail.price,
                    timestamp = Date().time,
                    freeShipping = if (articleDetail.isFreeShipping) 1 else 0,
                    thumbnail = articleDetail.thumbnail,
                    isMeliChoiceCandidate = if (isMeliChoiceCandidate) 1 else 0,
                    sellerName = sellerName,
                    sellerLink = sellerLink
                )
            )
        }
    }

    fun onBuy() {
        markEvent()
        currentState().articleDetail?.let {
            _event.value = Event.NavigationToBuy(articleLink =it.permalink)
        }
    }

    private fun markEvent() {
        currentState().articleDetail?.let {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO, Bundle().apply {
                putString(ARTICLE_ID_EVENT_KEY, it.id)
                putString(ARTICLE_TITLE_EVENT_KEY, it.title)
                putString(ARTICLE_PRICE_EVENT_KEY, it.originalPrice)
            })
        }
    }

    fun eventNone() {
        _event.value = Event.None
    }

    data class State(
        val loading: Boolean = false,
        val error: Boolean = false,
        val articleDetail: ArticleDetail? = null
    )

    sealed class Event {
        data class NavigationToBuy(val articleLink: String) : Event()
        object None : Event()
    }
}

private const val ARTICLE_ID_EVENT_KEY = "article_id"
private const val ARTICLE_TITLE_EVENT_KEY = "article_title"
private const val ARTICLE_PRICE_EVENT_KEY = "article_price"