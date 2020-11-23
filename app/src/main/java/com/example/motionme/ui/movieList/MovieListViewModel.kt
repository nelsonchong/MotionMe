package com.example.motionme.ui.movieList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.motionme.model.MovieSummary
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

    private val _data = MutableLiveData<List<MovieSummary>>().apply { value = emptyList() }
    val data: LiveData<List<MovieSummary>> = _data

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

                response.body()?.movies?.let {
                    _data.postValue((data.value ?: emptyList()) + it)
                }
            }

            override fun onFailure(call: Call<MovieSummaryResponse>, t: Throwable) {
                showToast(t)
            }
        })
    }

}

