package com.kevinserrano.supermeli.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.TypedValue
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kevinserrano.supermeli.lib.meli.model.ApiArticleDetailsResponse
import com.kevinserrano.supermeli.lib.meli.model.ApiSearchListResponse
import java.text.NumberFormat
import java.util.*
import kotlin.collections.List

fun animationForSplashScreen(
    onExitAnimation: () -> Unit,
    splashScreenView: SplashScreenViewProvider
) {
    val alpha = ObjectAnimator.ofFloat(
        splashScreenView.view,
        View.ALPHA,
        1f,
        0f
    )
    alpha.duration = 1500L
    alpha.doOnEnd {
        splashScreenView.remove()
        onExitAnimation()
    }
    alpha.start()
}

fun Context.openWebPage(url: String) {
    if (url.isEmpty())
        return
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun String.toHtml() = HtmlCompat.fromHtml(
    this,
    HtmlCompat.FROM_HTML_MODE_LEGACY
)

fun FragmentManager.showFragment(
    containerViewId: Int,
    fragment: Fragment,
    tag: String
) {
    this.beginTransaction().replace(
        containerViewId, fragment, tag
    ).commit()
}

fun FragmentManager.removeFragment() {
    val visibleFragment = fragments.findLast { fgm -> fgm.isVisible }
    visibleFragment?.let {
        beginTransaction().remove(visibleFragment).commit()
    }
}

fun Resources.getDips(dps: Int = 44): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dps.toFloat(),
        displayMetrics
    ).toInt()
}

fun Context.getDataFromAssets(fileName: String) = this.assets.open(fileName).bufferedReader().use {
    it.readText()
}

@SuppressLint("DiscouragedApi")
fun Context.getDrawableFromImageName(name: String) = resources.getIdentifier(
    name, "drawable",
    packageName
)

fun Double.toPriceFormat() =
    NumberFormat.getCurrencyInstance(Locale("es", "CO")).apply {
        maximumFractionDigits = 0
    }.format(this) ?: this.toString()

fun String.toSecureProtocol() =
    if (this.contains("http://")) this.replace("http://", "https://") else this

fun ApiSearchListResponse.Result.Installments?.getDueDetail() =
    if (this == null) "" else ("en ${quantity}x ${amount.toPriceFormat()}")

fun List<ApiArticleDetailsResponse.Attribute>.getAttributesText(): String {
    return this.joinToString(
        prefix = "<br/>",
        separator = "<br/>",
        postfix = "<br/>",
        transform = {
            if(it.valueName != null)
                "<font color='#3483F9'>- </font>${it.name}: <strong>${it.valueName}</strong>"
            else
                ""
        })
}