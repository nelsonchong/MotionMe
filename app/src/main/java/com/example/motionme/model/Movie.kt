package com.example.motionme.model

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

class Movie: MovieSummary() {
    @SerializedName("Rated") val rated: String = ""
    @SerializedName("Released") val released: String = ""
    @SerializedName("Runtime") val runtime: String = ""
    @SerializedName("Genre") val genre: String = ""
    @SerializedName("Director") val director: String = ""
    @SerializedName("Writer") val writer: String = ""
    @SerializedName("Actors") val actors: String = ""
    @SerializedName("Plot") val plot: String = ""
    @SerializedName("Language") val language: String = ""
    @SerializedName("Country") val country: String = ""
    @SerializedName("Awards") val awards: String = ""
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
    val oroduction: String = ""
    @SerializedName("Website")
    val website: String = ""
    @SerializedName("Response")
    val response: String = ""
    @SerializedName("Ratings")
    val ratings: List<Rating> = emptyList()
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