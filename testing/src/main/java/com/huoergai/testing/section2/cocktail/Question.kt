package com.huoergai.testing.section2.cocktail

/**
 * D&T: 2020-06-27 14:33
 * Des:
 */
class Question(
    val correctOption: String,
    val incorrectOption: String,
    val imageUrl: String? = null
) {

    var answeredOption: String? = null
        private set
    val isAnsweredCorrectly: Boolean
        get() {
            return correctOption == answeredOption
        }

    fun answer(option: String): Boolean {
        answeredOption = option
        if (option != correctOption && option != incorrectOption) {
            throw IllegalArgumentException("not a valid option")
        }

        answeredOption = option
        return isAnsweredCorrectly
    }

    // ----------------------- challenge ---------------------

    fun getOptions(options: List<String>, sort: ((List<String>) -> List<String>)?): List<String> {
        return if (sort == null) {
            options.shuffled()
        } else {
            sort(options)
        }
    }

}