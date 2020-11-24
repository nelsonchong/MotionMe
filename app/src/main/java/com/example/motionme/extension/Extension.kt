package com.example.motionme.extension

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    liveData.removeObservers(this)
    liveData.observe(this, Observer(body))
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun String.digitOnly(): String {
    return replace(Regex("[^\\d]"), "")
}

fun String.removeBracket(): String {
    return substringBefore("(").trim()
}