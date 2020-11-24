package com.example.motionme.ui.movieDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.motionme.model.Movie
import com.example.motionme.repo.MovieRepo
import com.example.motionme.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel @ViewModelInject constructor(
    private val repo: MovieRepo
) : BaseViewModel() {

    private val _data =
        MutableLiveData<List<MovieDetailAdapter.Model>>().apply { value = emptyList() }
    val data: LiveData<List<MovieDetailAdapter.Model>> = _data

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    fun load(imdbId: String) {
        if (imdbId.isEmpty()) {
            showToast("Something went wrong.")
            return
        }

        _loading.postValue(true)

        val call = repo.getMovieDetail(imdbId)
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                _loading.postValue(false)
                if (!response.isSuccessful) {
                    showToast(response.message())
                    return
                }

                val list = arrayListOf<MovieDetailAdapter.Model>()
                response.body()?.let {
                    list.add(MovieDetailAdapter.PosterModel(poster = it.poster))
                    list.add(MovieDetailAdapter.SmallSpaceModel())
                    list.add(
                        MovieDetailAdapter.TitleModel(
                            title = it.title,
                            info = it.mapInfo(),
                            imdbId = it.imdbId
                        )
                    )
                    list.add(MovieDetailAdapter.LargeSpaceModel())
                    list.add(MovieDetailAdapter.HeaderModel(text = "Plot Summary"))
                    list.add(MovieDetailAdapter.SmallSpaceModel())
                    list.add(MovieDetailAdapter.BodyModel(text = it.plot))
                    list.add(MovieDetailAdapter.LargeSpaceModel())
                    list.add(MovieDetailAdapter.HeaderModel(text = "Cast & Crew"))
                    list.addAll(it.mapCastModel())
                    list.add(MovieDetailAdapter.SmallSpaceModel())
                }

                _data.postValue(list)
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                _loading.postValue(false)
                showToast(t)
            }

        })
    }

}