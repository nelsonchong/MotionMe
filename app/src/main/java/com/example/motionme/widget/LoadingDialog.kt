package com.example.motionme.widget

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.motionme.R

class LoadingDialog(var context: Context) {

    private val dialog = Dialog(context)

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}