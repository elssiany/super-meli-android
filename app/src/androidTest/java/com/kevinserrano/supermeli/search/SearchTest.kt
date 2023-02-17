package com.kevinserrano.supermeli.search

import android.content.Context
import androidx.test.filters.MediumTest
import com.kevinserrano.supermeli.util.BaseUITest
import com.kevinserrano.supermeli.util.Page.Companion.on
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

@HiltAndroidTest
class SearchTest : BaseUITest() {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Test
    @MediumTest
    fun shouldSeeSearchScreenInHome() {
        on<SearchPage>()
            .launchView(
                context = context
            )
            .checkTitleTextIsVisible()
            .checkSetTextToInputText()
    }
}