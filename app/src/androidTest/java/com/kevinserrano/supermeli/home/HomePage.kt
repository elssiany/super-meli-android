package com.kevinserrano.supermeli.home

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import clickWithId
import com.kevinserrano.supermeli.home.ui.HomeActivity
import com.kevinserrano.supermeli.util.Page
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.util.waitUntilViewIsDisplayedById
import com.kevinserrano.supermeli.util.waitUntilViewIsDisplayedByText
import isTextNotDisplayed

class HomePage : Page() {

    fun launchView(
        context: Context
    ) = apply {
        Intent(context, HomeActivity::class.java).let {
            launchActivity<HomeActivity>(it).moveToState(Lifecycle.State.RESUMED)
        }
    }

    fun checkHomeTextIsVisible() = apply {
        waitUntilViewIsDisplayedByText(text = "Hola! \uD83D\uDC4B")
    }

    fun checkHomeImageProfileIsVisible() = apply {
        waitUntilViewIsDisplayedById(id = R.id.imgProfile)
    }

    fun checkHomeBtnSearchIsVisible() = apply {
        waitUntilViewIsDisplayedById(id = R.id.fab)
    }

    fun checkIfCategoriesIsVisible() = apply {
        waitUntilViewIsDisplayedById(id = R.id.rvCategories)
    }

    fun checkBtnSearch() = apply {
        clickWithId(id = R.id.fab)
    }

    fun checkHomeTextIsNoVisible() = apply {
        isTextNotDisplayed(text = "Hola! \uD83D\uDC4B")
    }
}
