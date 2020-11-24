package com.example.motionme.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.motionme.R

class CastView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var tvName: TextView
    private var tvRole: TextView

    var name: String = ""
        set(value) {
            field = value
            tvName.text = value
        }

    var role: String = ""
        set(value) {
            field = value
            tvRole.text = value
        }

    init {
        inflate(context, R.layout.cast_view, this)
        tvName = findViewById(R.id.tvName)
        tvRole = findViewById(R.id.tvRole)
    }

}