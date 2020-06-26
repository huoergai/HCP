package com.huoergai.testing.section1

/**
 * D&T: 2020-06-26 22:13
 * Des: test practises in chapter 3.
 */
class Chapter3 {

    /**
     * first red test
     */
    fun getSearchUrl0(query: String?) {

    }

    /**
     * first green test refactored from previous red test
     */
    fun getSearchUrl1(query: String?): String? {

        return null
    }

    /**
     * second green test
     */
    fun getSearchUrl2(query: String?): String? {

        return query
    }

    /**
     * refactored false positive from previous function
     */
    fun getSearchUrl2Re(query: String?): String? {
        return query?.let { "https://www.google.com/search?q=${query}" }
    }


}