package com.kevinserrano.supermeli.detail

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import clickWithId
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.detail.ui.ARTICLE_ID_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.ArticleDetailActivity
import com.kevinserrano.supermeli.detail.ui.IS_MELI_CHOICE_CANDIDATE_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.SELLER_NAME_KEY_PARAM
import com.kevinserrano.supermeli.detail.ui.SELLER_lINK_KEY_PARAM
import com.kevinserrano.supermeli.util.Page
import com.kevinserrano.supermeli.util.waitUntilViewIsDisplayedByText
import isTextNotDisplayed
import textDisplayed

class ArticleDetailPage : Page() {

    fun launchView(
        context: Context,
        articleId: String,
        isMeliChoiceCandidate: Boolean,
        sellerName: String,
        sellerLink: String
    ) = apply {
        Intent(context, ArticleDetailActivity::class.java).apply {
            putExtra(ARTICLE_ID_KEY_PARAM, articleId)
            putExtra(IS_MELI_CHOICE_CANDIDATE_KEY_PARAM, isMeliChoiceCandidate)
            putExtra(SELLER_NAME_KEY_PARAM, sellerName)
            putExtra(SELLER_lINK_KEY_PARAM, sellerLink)
        }.let {
            launchActivity<ArticleDetailActivity>(it).moveToState(Lifecycle.State.RESUMED)
        }
    }

    fun checkTitleTextIsVisible() = apply {
        waitUntilViewIsDisplayedByText(text = "Samsung Galaxy A03s Dual Sim 64 Gb Negro 4 Gb Ram")
    }

    fun checkSellerNameTextIsVisible() = apply {
        textDisplayed(text = "QUILLATIME BQ")
    }

    fun checkChoiceCandidateTextIsVisible() = apply {
        textDisplayed(text = "RECOMENDADO")
    }

    fun checkIfBtnBuyIsVisible() = apply {
        textDisplayed(text = "Comprar ahora")
    }

    fun clickBtnBuy() = apply {
        clickWithId(id = R.id.btnBuy)
    }

    fun checkBtnBuyTextIsNoVisible() = apply {
        isTextNotDisplayed(text = "Comprar ahora")
    }
}
