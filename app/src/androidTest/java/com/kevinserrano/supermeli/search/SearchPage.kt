package com.kevinserrano.supermeli.search

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.search.ui.SearchActivity
import com.kevinserrano.supermeli.util.Page
import com.kevinserrano.supermeli.util.waitUntilViewIsDisplayedByText
import isViewDisplayed
import setTextOnField

class SearchPage : Page() {

    fun launchView(
        context: Context
    ) = apply {
        Intent(context, SearchActivity::class.java).let {
            launchActivity<SearchActivity>(it).moveToState(Lifecycle.State.RESUMED)
        }
    }

    fun checkTitleTextIsVisible() = apply {
        isViewDisplayed(id = R.id.inputQuery)
    }

    fun checkSetTextToInputText() = apply {
        setTextOnField(id = R.id.inputQuery, text = "samsung")
    }
}
