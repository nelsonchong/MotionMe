package com.example.motionme.repo

import com.example.motionme.model.Movie
import com.example.motionme.network.ApiService
import com.example.motionme.network.response.MovieSummaryResponse
import retrofit2.Call

interface MovieRepo : BaseRepo {
    fun searchMovieList(query: String, page: Int): Call<MovieSummaryResponse>
    fun getMovieDetail(imdbId: String): Call<Movie>
}

class MovieRepoImpl(
    private val apiService: ApiService
) : MovieRepo {

    override fun searchMovieList(query: String, page: Int): Call<MovieSummaryResponse> {
        return apiService.getMovieList(query, page)
    }

    override fun getMovieDetail(imdbId: String): Call<Movie> {
        return apiService.getMovieDetail(imdbId)
    }
}