package com.ugesh.qa.services

import com.ugesh.qa.dtos.payloads.answers.AnswerRequestPayload
import com.ugesh.qa.exceptions.InvalidParameterException
import com.ugesh.qa.repositories.AnswerRepository
import com.ugesh.qa.repositories.QuestionRepository
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class AnswerServiceTest {
    private val questionRepository: QuestionRepository = mockk()
    private val answerRepository: AnswerRepository = mockk()
    private val answerService = AnswerService(questionRepository = questionRepository, answerRepository = answerRepository)
    private val questionId = UUID.randomUUID().toString().replace("-", "")

    @BeforeEach
    fun init() {
        clearMocks(questionRepository, answerRepository)
    }

    @Nested
    inner class CreateAnswer {
        @Test
        fun `creating answer should require answer text`() {
            assertThrows<InvalidParameterException> {
                answerService.createAnswer(questionId = questionId, answerRequestPayload = AnswerRequestPayload(answer = ""))
            }
        }

        @Test
        fun `answer should require some text`() {
            assertThrows<InvalidParameterException> {
                answerService.createAnswer(questionId = questionId, answerRequestPayload = AnswerRequestPayload())
            }
        }
    }
}
