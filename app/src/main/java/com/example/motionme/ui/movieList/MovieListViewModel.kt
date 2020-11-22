package com.example.motionme.ui.movieList

import androidx.hilt.lifecycle.ViewModelInject
import com.example.motionme.network.response.MovieSummaryResponse
import com.example.motionme.repo.MovieRepo
import com.example.motionme.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MovieListViewModel @ViewModelInject constructor(
    private val repo: MovieRepo
) : BaseViewModel() {

    fun search(query: String) {
        val call = repo.searchMovieList(query)
        call.enqueue(object : Callback<MovieSummaryResponse> {
            override fun onResponse(
                call: Call<MovieSummaryResponse>,
                response: Response<MovieSummaryResponse>
            ) {
                if (!response.isSuccessful) {
                    showToast(response.message())
                    return
                }

                val titles = response.body()?.movies?.map { it.title } ?: emptyList()
                titles.forEach {
                    Timber.tag("###").d(it)
                }
            }

            override fun onFailure(call: Call<MovieSummaryResponse>, t: Throwable) {
                showToast(t)
            }
        })
    }

}

