package com.example.motionme.ui.base

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if ((ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText) {
            val outLocation = IntArray(2)
            view.getLocationOnScreen(outLocation)
            val x = ev.rawX + view.left - outLocation[0]
            val y = ev.rawY + view.top - outLocation[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom) {
                hideKeyboard(this)
                view.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard(context: Activity) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (context.currentFocus != null) {
            imm.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        }
    }

}