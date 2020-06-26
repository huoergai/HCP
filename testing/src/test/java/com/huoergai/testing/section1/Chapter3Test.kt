package com.huoergai.testing.section1

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

/**
 * D&T: 2020-06-26 22:16
 * Des:
 */

internal class Chapter3Test {
    private lateinit var chapter3: Chapter3

    @BeforeEach
    fun initTest() {
        chapter3 = Chapter3()
    }

    @Disabled("bug fixed: Red Test has been refactored to green test")
    @Test
    fun testGetSearchUrlRedTest() {
        chapter3.getSearchUrl0("query")
    }

    /**
     *
     */
    @Test
    fun testGetSearchUrlReturnNullIfInputNull() {
        val stringUrl = chapter3.getSearchUrl1(null)
        if (stringUrl == null) {
            println("success")
        } else {
            fail("result was not null when query = null!")
        }
    }

    @Test
    fun testGetSearchUrlReturnNotNullWhenQueryIsNotNull() {
        val stringUrl = chapter3.getSearchUrl2("toaster")
        if (stringUrl != null) {
            println("success")
        } else {
            fail("result was null when query != null")
        }
    }

    @Test
    fun testGetSearchUrlFalsePositive() {
        val stringUrl = chapter3.getSearchUrl2("toaster")
        if (stringUrl?.contains("toaster") == true) {
            println("success")
        } else {
            fail("result did not contain query")
        }
    }

    @Test
    fun testGetSearchUrlFalsePositiveRe() {
        val stringUrl = chapter3.getSearchUrl2Re("query")
        if (stringUrl?.contains("query") == true) {
            println("success")
        } else {
            fail("result did not contain query")
        }
    }

}