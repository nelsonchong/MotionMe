package com.example.motionme

import com.example.motionme.extension.beforeBracket
import com.example.motionme.extension.convertToHourMinFormat
import com.example.motionme.extension.digitOnly
import org.junit.Assert
import org.junit.Test

class StringUnitTest {

    @Test
    fun digitOnly_isEqual() {
        val list = listOf(
            Pair("12345", "12345"),
            Pair("12345 ", "12345"),
            Pair(" 12345", "12345"),
            Pair("1 2 3", "123"),
            Pair("1234567890", "1234567890"),
            Pair("0123", "0123"),
            Pair("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", ""),
            Pair("Abc123", "123"),
            Pair("123Abc", "123"),
            Pair("A1B2C3", "123"),
            Pair("!@#$%^&*()-_=+,<.>/?;:'\"[{]}\\| ", ""),
            Pair("!@#123abc", "123"),
            Pair("123 \n 456", "123456")
        )

        list.forEach {
            Assert.assertEquals(it.second, it.first.digitOnly())
        }
    }

    @Test
    fun beforeBracket_isEqual() {
        val list = listOf(
            Pair("ABC(TO REMOVE)", "ABC"),
            Pair("ABC (TO REMOVE)", "ABC"),
            Pair("ABC ((TO REMOVE))", "ABC"),
            Pair("(ABC)(DEF)", ""),
            Pair("ABC ( \n TO REMOVE ", "ABC")
        )

        list.forEach {
            Assert.assertEquals(it.second, it.first.beforeBracket())
        }
    }

    @Test
    fun convertToHourMinFormat_isEqual() {
        val list = listOf(
            Pair("0 min", "0h 0min"),
            Pair("", "0h 0min"),
            Pair("60 min", "1h 0min"),
            Pair("59 min", "0h 59min"),
            Pair("1 min", "0h 1min"),
            Pair("120 min", "2h 0min")
        )

        list.forEach {
            Assert.assertEquals(it.second, it.first.convertToHourMinFormat())
        }
    }
}