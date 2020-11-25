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

    private var title: String = ""

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
                    title = it.title
                    list.add(
                        MovieDetailAdapter.PosterModel(
                            poster = it.poster,
                            rating = "${it.imdbRating}/10",
                            numOfVotes = it.imdbVotes,
                            metascore = it.metascore
                        )
                    )
                    list.add(MovieDetailAdapter.SmallSpaceModel())
                    list.add(
                        MovieDetailAdapter.TitleModel(
                            title = it.title,
                            info = it.mapInfo()
                        )
                    )
                    list.add(MovieDetailAdapter.SmallSpaceModel())
                    list.add(MovieDetailAdapter.GenreModel(
                        genres = it.genre.split(",").map { genre -> genre.trim() }
                    ))
                    list.add(MovieDetailAdapter.LargeSpaceModel())
                    list.add(MovieDetailAdapter.HeaderModel(text = "Plot Summary"))
                    list.add(MovieDetailAdapter.SmallSpaceModel())
                    list.add(MovieDetailAdapter.BodyModel(text = it.plot))
                    list.add(MovieDetailAdapter.LargeSpaceModel())
                    list.add(MovieDetailAdapter.HeaderModel(text = "Cast & Crew"))
                    list.add(MovieDetailAdapter.SmallSpaceModel())
                    it.mapCastModel().forEach { castModel ->
                        list.add(castModel)
                        list.add(MovieDetailAdapter.SmallSpaceModel())
                    }
                }

                _data.postValue(list)
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                _loading.postValue(false)
                showToast(t)
            }

        })
    }

    fun mapYoutubeUrl(): String {
        return "https://www.youtube.com/results?search_query=$title"
    }

}