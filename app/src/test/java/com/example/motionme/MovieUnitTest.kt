package com.example.motionme

import com.example.motionme.model.Movie
import com.example.motionme.model.MovieSummary
import com.example.motionme.model.mapModel
import org.junit.Assert
import org.junit.Test

class MovieUnitTest {

    private val listCount0 = emptyList<MovieSummary>()
    private val listCount1 = listOf(MovieSummary())
    private val listCount2 = listOf(MovieSummary(), MovieSummary())
    private val listCount3 = listOf(MovieSummary(), MovieSummary(), MovieSummary())
    private val listCount4 = listOf(MovieSummary(), MovieSummary(), MovieSummary(), MovieSummary())

    private val result0 = listCount0.mapModel()
    private val result1 = listCount1.mapModel()
    private val result2 = listCount2.mapModel()
    private val result3 = listCount3.mapModel()
    private val result4 = listCount4.mapModel()

    @Test
    fun movieSummaryMapModelCount_isEqual() {
        Assert.assertEquals(0, result0.size)
        Assert.assertEquals(1, result1.size)
        Assert.assertEquals(1, result2.size)
        Assert.assertEquals(2, result3.size)
        Assert.assertEquals(2, result4.size)
    }

    @Test
    fun movieSummaryMapModel_movieInfoRight_isNull() {
        Assert.assertNull(result1.last().movieInfoRight)
        Assert.assertNull(result3.last().movieInfoRight)
    }

    @Test
    fun movieSummaryMapModel_movieInfoRight_isNotNull() {
        Assert.assertNotNull(result2.last().movieInfoRight)
        Assert.assertNotNull(result4.last().movieInfoRight)
    }

    @Test
    fun movieMapCastModelCount_isEqual() {
        val list = arrayListOf<Pair<Int, Movie>>()

        val movie1 = Movie()
        list.add(Pair(0, movie1))

        val movie2 = Movie()
        movie2.director = "Cast A, Cast B, Cast C"
        list.add(Pair(1, movie2))

        val movie3 = Movie()
        movie3.director = "Cast A, Cast B, Cast C, Cast D"
        list.add(Pair(1, movie3))

        val movie4 = Movie()
        movie4.director = "Cast A, Cast B, Cast C, Cast D, Cast E"
        list.add(Pair(2, movie4))

        list.forEach {
            Assert.assertEquals(it.first, it.second.mapCastModel().size)
        }
    }

    @Test
    fun movieMapCastModelCastInfoCount_isEqual() {
        val list = arrayListOf<Pair<Int, Movie>>()

        val movie1 = Movie()
        movie1.director = "Cast A, Cast B, Cast C"
        movie1.writer = "Cast D, Cast E, Cast F"
        movie1.actors = "Cast G, Cast H, Cast I"
        list.add(Pair(9, movie1))

        val movie2 = Movie()
        movie2.director = "Cast A, Cast B, Cast C"
        movie2.writer = "Cast A, Cast B, Cast C"
        movie2.actors = "Cast A, Cast B, Cast C"
        list.add(Pair(3, movie2))

        val movie3 = Movie()
        movie3.writer = "Cast D, Cast E, Cast F"
        movie3.actors = "Cast G, Cast H, Cast I"
        list.add(Pair(6, movie3))

        val movie4 = Movie()
        movie4.director = "Cast A, Cast B, Cast C"
        movie4.actors = "Cast G, Cast H, Cast I"
        list.add(Pair(6, movie4))

        val movie5 = Movie()
        movie5.director = "Cast A, Cast B, Cast C"
        movie5.writer = "Cast D, Cast E, Cast F"
        list.add(Pair(6, movie5))

        list.forEach {
            var totalNumOfCastInfo = 0
            it.second.mapCastModel().forEach { castModel ->
                totalNumOfCastInfo += castModel.data.size
            }
            Assert.assertEquals(it.first, totalNumOfCastInfo)
        }
    }

}