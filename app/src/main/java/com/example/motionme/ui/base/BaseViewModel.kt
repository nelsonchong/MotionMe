package com.example.motionme.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val _showLoading: MutableLiveData<Unit> = MutableLiveData()
    val _hideLoading: MutableLiveData<Unit> = MutableLiveData()
    val _showError: MutableLiveData<String> = MutableLiveData()
    val _showToast: MutableLiveData<String> = MutableLiveData()

    fun showLoading() {
        _showLoading.postValue(Unit)
    }

    fun hideLoading() {
        _hideLoading.postValue(Unit)
    }

    fun showError(error: Throwable) {
        _showError.postValue(error.localizedMessage ?: "Something went wrong.")
    }

    fun showError(error: String) {
        _showError.postValue(error)
    }

    fun showToast(message: String) {
        _showToast.postValue(message)
    }

    fun showToast(error: Throwable) {
        _showToast.postValue(error.localizedMessage ?: "Something went wrong.")
    }
}