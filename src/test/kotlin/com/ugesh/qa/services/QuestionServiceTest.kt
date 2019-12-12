package com.ugesh.qa.services

import com.ugesh.qa.dtos.QuestionDto
import com.ugesh.qa.dtos.payloads.questions.QuestionRequestPayload
import com.ugesh.qa.exceptions.InvalidParameterException
import com.ugesh.qa.models.Question
import com.ugesh.qa.repositories.QuestionRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class QuestionServiceTest {
    //creating mocks once
    private val questionRepository: QuestionRepository = mockk()
    private val answerService: AnswerService = mockk()
    private val questionService = QuestionService(questionRepository = questionRepository, answerService = answerService)

    @BeforeEach
    fun init() {
        clearMocks(questionRepository, answerService)
    }

    @Nested
    inner class CreateQuestion {
        @Test
        fun `creating a question should require question title`() {
           assertThrows<InvalidParameterException> {
                questionService.createQuestion(questionRequestPayload = QuestionRequestPayload(questionDescription = "xyz"))
           }
        }

        @Test
        fun `creating a question should require question description`() {
            assertThrows<InvalidParameterException> {
                questionService.createQuestion(questionRequestPayload = QuestionRequestPayload(questionTitle = "xyz"))
            }
        }

        @Test
        fun `creating question should work`() {
            val createdQuestion = createQuestion(title="title", description = "description")
            assertNotNull(createdQuestion.questionId)
            assertEquals("title", createdQuestion.questionTitle)
            assertEquals("description", createdQuestion.questionDescription)
            assertEquals(0, createdQuestion.totalAnswers)
            assertEquals(0, createdQuestion.views)
            assertEquals(0, createdQuestion.votes)
        }
    }

    private fun createQuestion(title: String? = null, description: String? = null): QuestionDto {
        every { questionRepository.save<Question>(any())} returns Question(
                id = 1,
                questionId = UUID.randomUUID().toString().replace("-", ""),
                questionTitle = title!!,
                questionDescription = description!!,
                askedAt = LocalDateTime.now().toString()
        )
        return questionService.createQuestion(questionRequestPayload = QuestionRequestPayload(
            questionTitle = title,
            questionDescription = description
        ))
    }
}
