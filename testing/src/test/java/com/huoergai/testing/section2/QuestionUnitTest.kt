package com.huoergai.testing.section2

import com.huoergai.testing.section2.section2.Question
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * D&T: 2020-06-27 14:35
 * Des:
 */

class QuestionUnitTest {
    private lateinit var question: Question

    @BeforeEach
    fun initTest() {
        question = Question("CORRECT", "INCORRECT")
    }

    @Test
    fun whenCreatingQuestion_shouldNotHaveAnsweredOption() {
        assertNull(question.answeredOption)
    }

    @Test
    fun whenAnswering_shouldHaveAnsweredOption() {
        question.answer("INCORRECT")
        assertEquals("INCORRECT", question.answeredOption)
    }

    @Test
    fun whenAnswering_withCorrectOption_shouldReturnTrue() {
        val ret = question.answer("CORRECT")
        assertTrue(ret)
    }

    @Test
    fun whenAnswering_withIncorrectOption_shouldReturnFalse() {
        val ret = question.answer("INCORRECT")
        assertFalse(ret)

    }

    @Test
    fun whenAnswering_withInvalidOption_shouldThrowException() {
        val iae = assertThrows(IllegalArgumentException::class.java) {
            question.answer("INVALID")
        }
        assertEquals("not a valid option", iae.message)
    }

}