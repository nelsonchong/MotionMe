package com.example.motionme.network

import com.example.motionme.constant.Constant
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?apikey=${Constant.API_KEY}")
    fun getMovieList(@Query("s") search: String, @Query("type") type: String = "movie")

    @GET("?apikey=${Constant.API_KEY}")
    fun getMovieDetail(@Query("i") id: String)

}