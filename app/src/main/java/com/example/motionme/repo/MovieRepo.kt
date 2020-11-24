package com.example.motionme.repo

import com.example.motionme.network.ApiService
import com.example.motionme.network.response.MovieSummaryResponse
import retrofit2.Call

interface MovieRepo : BaseRepo {
    fun searchMovieList(query: String, page: Int): Call<MovieSummaryResponse>
}

class MovieRepoImpl(
    private val apiService: ApiService
) : MovieRepo {

    override fun searchMovieList(query: String, page: Int): Call<MovieSummaryResponse> {
        return apiService.getMovieList(query, page)
    }

}