package com.example.motionme.ui.movieList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.motionme.model.mapModel
import com.example.motionme.network.response.MovieSummaryResponse
import com.example.motionme.repo.MovieRepo
import com.example.motionme.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel @ViewModelInject constructor(
    private val repo: MovieRepo
) : BaseViewModel() {

    private val _data =
        MutableLiveData<List<MovieListAdapter.Model>>().apply { value = emptyList() }
    val data: LiveData<List<MovieListAdapter.Model>> = _data

    private var query: String = ""
    private var page: Int = 1
    private var isLoading: Boolean = false
    private var totalResults: Int = 0

    fun search(query: String) {
        this.query = query
        page = 1
        isLoading = true
        insertLoading(true)
        searchMovieList(query, page)
    }

    fun load() {
        if (isLoading) return

        data.value?.count()?.let {
            if (it >= totalResults) return
        }

        page += 1
        isLoading = true
        insertLoading(false)
        searchMovieList(query, page)
    }

    fun isLastItemShown(row: Int): Boolean {
        return data.value?.let {
            row == it.count() - 1
        } ?: false
    }

    private fun searchMovieList(query: String, page: Int) {
        val call = repo.searchMovieList(query, page)
        call.enqueue(object : Callback<MovieSummaryResponse> {
            override fun onResponse(
                call: Call<MovieSummaryResponse>,
                response: Response<MovieSummaryResponse>
            ) {
                isLoading = false

                if (!response.isSuccessful) {
                    showToast(response.message())
                    return
                }

                totalResults = response.body()?.totalResults?.toInt() ?: 0

                response.body()?.movies?.let {
                    val oldData =
                        if (data.value?.lastOrNull()?.type == MovieListAdapter.Type.Loading) data.value?.dropLast(1)
                        else data.value

                    val newData = it.mapModel()

                    _data.postValue((oldData ?: emptyList()) + newData)
                }
            }

            override fun onFailure(call: Call<MovieSummaryResponse>, t: Throwable) {
                isLoading = false
                if (data.value?.lastOrNull()?.type == MovieListAdapter.Type.Loading) {
                    _data.postValue((data.value?.dropLast(1)) ?: emptyList())
                }

                showToast(t)
            }
        })
    }

    private fun insertLoading(clear: Boolean) {
        if (clear) {
            _data.postValue(listOf(MovieListAdapter.LoadingModel()))
        } else {
            if (data.value?.lastOrNull()?.type == MovieListAdapter.Type.Loading) return

            _data.postValue((data.value ?: emptyList()) + MovieListAdapter.LoadingModel())
        }
    }
}

