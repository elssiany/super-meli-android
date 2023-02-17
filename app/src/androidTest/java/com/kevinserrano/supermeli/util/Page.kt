package com.kevinserrano.supermeli.util

import androidx.test.espresso.Espresso

open class Page {
    companion object {
        inline fun <reified T : Page> on(): T {
            return Page().on()
        }
    }

    inline fun <reified T : Page> on(): T {
        return T::class.constructors.first().call()
    }

    fun back(): Page {
        Espresso.pressBack()
        return this
    }
}
