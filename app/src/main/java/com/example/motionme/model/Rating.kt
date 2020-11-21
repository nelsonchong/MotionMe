package com.example.motionme.model

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("Source") val source: String,
    @SerializedName("Value") val value: String
)
