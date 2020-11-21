package com.example.motionme.network.response

import com.example.motionme.model.MovieSummary
import com.google.gson.annotations.SerializedName

data class MovieSummaryResponse(
    @SerializedName("Search") val movies: List<MovieSummary>,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: String
)