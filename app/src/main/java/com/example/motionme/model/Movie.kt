package com.example.motionme.model

import com.example.motionme.extension.beforeBracket
import com.example.motionme.extension.convertToHourMinFormat
import com.example.motionme.ui.movieDetail.MovieDetailAdapter
import com.example.motionme.ui.movieList.MovieListAdapter
import com.google.gson.annotations.SerializedName

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
    var director: String = ""

    @SerializedName("Writer")
    var writer: String = ""

    @SerializedName("Actors")
    var actors: String = ""

    @SerializedName("Plot")
    val plot: String = ""

    @SerializedName("Language")
    val language: String = ""

    @SerializedName("Country")
    val country: String = ""

    @SerializedName("Awards")
    val awards: String = ""

    @SerializedName("Metascore")
    val metascore: String = ""

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
            info = info.plus("     ${runtime.convertToHourMinFormat()}")
        }

        return info
    }

    fun mapCastModel(): List<MovieDetailAdapter.CastModel> {
        val infos = arrayListOf<MovieDetailAdapter.CastInfo>()
        director.split(",").forEach { name ->
            val formatted = name.beforeBracket()
            if (formatted.isNotEmpty() && infos.indexOfFirst { it.name == formatted } == -1) {
                infos.add(MovieDetailAdapter.CastInfo(formatted, "Director"))
            }
        }

        actors.split(",").forEach { name ->
            val formatted = name.beforeBracket()
            if (formatted.isNotEmpty() && infos.indexOfFirst { it.name == formatted } == -1) {
                infos.add(MovieDetailAdapter.CastInfo(formatted, "Actor"))
            }
        }

        writer.split(",").forEach { name ->
            val formatted = name.beforeBracket()
            if (formatted.isNotEmpty() && infos.indexOfFirst { it.name == formatted } == -1) {
                infos.add(MovieDetailAdapter.CastInfo(formatted, "Writer"))
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