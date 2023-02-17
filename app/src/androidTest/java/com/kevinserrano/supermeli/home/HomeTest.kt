package com.kevinserrano.supermeli.home

import android.content.Context
import androidx.test.filters.MediumTest
import com.kevinserrano.supermeli.util.BaseUITest
import com.kevinserrano.supermeli.util.Page.Companion.on
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

@HiltAndroidTest
class HomeTest: BaseUITest() {

    @Inject @ApplicationContext lateinit var context: Context

    @Test
    @MediumTest
    fun shouldSeeCompleteHomeScreenInHome() {
        on<HomePage>()
            .launchView(context)
            .checkHomeBtnSearchIsVisible()
            .checkHomeTextIsVisible()
            .checkHomeImageProfileIsVisible()
            .checkIfCategoriesIsVisible()
    }

    @Test
    @MediumTest
    fun shouldSeeSearchScreenFromHome() {
        on<HomePage>()
            .launchView(context)
            .checkHomeTextIsVisible()
            .checkBtnSearch()
            .checkHomeTextIsNoVisible()
    }
}