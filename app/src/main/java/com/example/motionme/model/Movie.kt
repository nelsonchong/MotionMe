package com.example.motionme.model

import com.example.motionme.extension.digitOnly
import com.example.motionme.extension.removeBracket
import com.example.motionme.ui.movieDetail.MovieDetailAdapter
import com.example.motionme.ui.movieList.MovieListAdapter
import com.google.gson.annotations.SerializedName
import timber.log.Timber

open class MovieSummary {
    @SerializedName("Title")
    val title: String = ""

    @SerializedName("Year")
    val year: String = ""

    @SerializedName("imdbID")
    val imdbId: String = ""

    @SerializedName("Type")
    val type: String = ""

    @SerializedName("Poster")
    val poster: String = ""

    fun mapMovieInfo(): MovieListAdapter.MovieInfo {
        return MovieListAdapter.MovieInfo(
            imdbId = imdbId,
            title = title,
            year = year,
            poster = poster
        )
    }
}

class Movie : MovieSummary() {
    @SerializedName("Rated")
    val rated: String = ""

    @SerializedName("Released")
    val released: String = ""

    @SerializedName("Runtime")
    val runtime: String = ""

    @SerializedName("Genre")
    val genre: String = ""

    @SerializedName("Director")
    val director: String = ""

    @SerializedName("Writer")
    val writer: String = ""

    @SerializedName("Actors")
    val actors: String = ""

    @SerializedName("Plot")
    val plot: String = ""

    @SerializedName("Language")
    val language: String = ""

    @SerializedName("Country")
    val country: String = ""

    @SerializedName("Awards")
    val awards: String = ""

    @SerializedName("Metascore")
    val metaScore: String = ""

    @SerializedName("imdbRating")
    val imdbRating: String = ""

    @SerializedName("imdbVotes")
    val imdbVotes: String = ""

    @SerializedName("DVD")
    val dvd: String = ""

    @SerializedName("BoxOffice")
    val boxOffice: String = ""

    @SerializedName("Production")
    val production: String = ""

    @SerializedName("Website")
    val website: String = ""

    @SerializedName("Response")
    val response: String = ""

    @SerializedName("Ratings")
    val ratings: List<Rating> = emptyList()

    fun mapInfo(): String {
        var info = year
        if (rated.isNotEmpty()) {
            info = info.plus("     $rated")
        }
        if (runtime.isNotEmpty()) {
            val digits = runtime.digitOnly().toIntOrNull() ?: 0
            val hour: Int = (digits / 60)
            val minute: Int = digits % 60

            Timber.tag("###").d("Runtime: $runtime")
            Timber.tag("###").d("Digits: $digits")
            Timber.tag("###").d("Hour: $hour")
            Timber.tag("###").d("Minute: $minute")

            info = info.plus("     ${hour}h ${minute}min")
        }

        return info
    }

    fun mapCastModel(): List<MovieDetailAdapter.CastModel> {
        val infos = arrayListOf<MovieDetailAdapter.CastInfo>()
        director.split(",").forEach { name ->
            if (infos.indexOfFirst { it.name == name } == -1) {
                infos.add(MovieDetailAdapter.CastInfo(name.removeBracket(), "Director"))
            }
        }

        actors.split(",").forEach { name ->
            if (infos.indexOfFirst { it.name == name } == -1) {
                infos.add(MovieDetailAdapter.CastInfo(name.removeBracket(), "Actor"))
            }
        }

        writer.split(",").forEach { name ->
            if (infos.indexOfFirst { it.name == name } == -1) {
                infos.add(MovieDetailAdapter.CastInfo(name.removeBracket(), "Writer"))
            }
        }

        val list = arrayListOf<MovieDetailAdapter.CastModel>()
        infos.forEach {
            if (list.isEmpty() || list.last().data.size == 4) {
                list.add(MovieDetailAdapter.CastModel(arrayListOf()))
            }
            list.last().data.add(it)
        }

        return list
    }
}

fun List<MovieSummary>.mapModel(): List<MovieListAdapter.MovieGridModel> {
    val list = arrayListOf<MovieListAdapter.MovieGridModel>()

    forEach {
        if (list.isNotEmpty() && list.lastOrNull()?.movieInfoRight == null) {
            list.lastOrNull()?.movieInfoRight = it.mapMovieInfo()
        } else {
            list.add(MovieListAdapter.MovieGridModel(movieInfoLeft = it.mapMovieInfo()))
        }
    }

    return list
}