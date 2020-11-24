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

// To retrieve numeric characters from string
fun String.digitOnly(): String {
    return replace(Regex("[^\\d]"), "")
}

// To retrieve string before char (
// eg. "ABC (DEF)" to "ABC"
fun String.beforeBracket(): String {
    return substringBefore("(").trim()
}

// To convert minutes to hour and minute format
// eg. "90 min" to "1h 30min"
fun String.convertToHourMinFormat(): String {
    val digits = digitOnly().toIntOrNull() ?: 0
    val hour: Int = (digits / 60)
    val minute: Int = digits % 60
    return "${hour}h ${minute}min"
}