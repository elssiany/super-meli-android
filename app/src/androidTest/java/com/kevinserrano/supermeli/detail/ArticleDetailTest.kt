package com.kevinserrano.supermeli.detail

import android.content.Context
import androidx.test.filters.MediumTest
import com.kevinserrano.supermeli.util.BaseUITest
import com.kevinserrano.supermeli.util.Page.Companion.on
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

@HiltAndroidTest
class ArticleDetailTest : BaseUITest() {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Test
    @MediumTest
    fun shouldSeeArticleDetailsScreen() {
        on<ArticleDetailPage>()
            .launchView(
                context = context,
                articleId = "MCO1228577020",
                isMeliChoiceCandidate = true,
                sellerName = "QUILLATIME BQ",
                sellerLink = "http://perfil.mercadolibre.com.co/QUILLATIME+BQ"
            )
            .checkTitleTextIsVisible()
            .checkIfBtnBuyIsVisible()
            .checkSellerNameTextIsVisible()
            .checkChoiceCandidateTextIsVisible()
    }

    @Test
    @MediumTest
    fun shouldBuyArticleScreenFromDetails() {
        on<ArticleDetailPage>()
            .launchView(
                context = context,
                articleId = "MCO1228577020",
                isMeliChoiceCandidate = true,
                sellerName = "QUILLATIME BQ",
                sellerLink = "http://perfil.mercadolibre.com.co/QUILLATIME+BQ"
            )
            .checkTitleTextIsVisible()
            .clickBtnBuy()
            .checkBtnBuyTextIsNoVisible()
    }
}