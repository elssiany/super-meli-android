package com.kevinserrano.supermeli.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseUITest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    open fun setup() {
        hiltRule.inject()
    }
}
