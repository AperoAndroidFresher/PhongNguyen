package com.hoaiphong.composeui.utils

import android.content.Context
import android.os.Build

fun getCurrentLanguageCode(context: Context): String {
    val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0]
    } else {
        @Suppress("DEPRECATION")
        context.resources.configuration.locale
    }
    return locale.language
}
fun getLanguageNameFromCode(code: String): String {
    return when (code) {
        "en" -> "English"
        "ko" -> "Korean"
        "fr" -> "French"
        "vi" -> "Vietnamese"
        else -> "English"
    }
}
