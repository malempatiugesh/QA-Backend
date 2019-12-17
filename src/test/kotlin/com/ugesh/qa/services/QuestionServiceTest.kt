package com.ugesh.qa.services

import com.ugesh.qa.dtos.QuestionDto
import com.ugesh.qa.dtos.payloads.questions.QuestionRequestPayload
import com.ugesh.qa.exceptions.InvalidParameterException
import com.ugesh.qa.models.Question
import com.ugesh.qa.repositories.QuestionRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class QuestionServiceTest {
    private val questionRepository: QuestionRepository = mockk()
    private val answerService: AnswerService = mockk()
    private val questionService = QuestionService(questionRepository = questionRepository, answerService = answerService)
    private val questionId = UUID.randomUUID().toString().replace("-", "")

    @BeforeEach
    fun init() {
        clearMocks(questionRepository, answerService)
    }

    @Nested
    inner class CreateQuestion {
        @Test
        fun `question should require question title`() {
           assertThrows<InvalidParameterException> { questionService.createQuestion(
                questionRequestPayload = QuestionRequestPayload(questionDescription = "xyz")
           )}
        }

        @Test
        fun `question should require question description`() {
            assertThrows<InvalidParameterException> { questionService.createQuestion(
                questionRequestPayload = QuestionRequestPayload(questionTitle = "xyz")
            )}
        }

        @Test
        fun `question creation should work`() {
            val question = questions()[0]
            val createdQuestion = createQuestion()
            assertNotNull(createdQuestion.questionId)
            assertEquals(question.questionTitle, createdQuestion.questionTitle)
            assertEquals(question.questionDescription, createdQuestion.questionDescription)
            assertEquals(0, createdQuestion.totalAnswers)
            assertEquals(0, createdQuestion.views)
            assertEquals(0, createdQuestion.votes)

            verify { questionRepository.save<Question>(any()) }
        }
    }

    @Nested
    inner class GetQuestions {
        @Test
        fun `getting all questions should work`() {
            every {questionRepository.findAll()} returns questions()

            val questions = questionService.getQuestions()
            assertEquals(2, questions.total)
            assertNotNull(questions.questions[0].questionId)
            assertNotNull(questions.questions[1].questionId)

            verify { questionRepository.findAll()}
        }
    }

    @Nested
    inner class GetQuestion {
        @Test
        fun `getting a single question should work`() {
            every { questionRepository.findByQuestionId(questionId) } returns questions()[0]
            every { questionRepository.save<Question>(any())} returns Question(
                    id = 1,
                    questionId = questionId,
                    questionTitle = "what is postman?",
                    questionDescription = "why and how postman is useful?",
                    askedAt = LocalDateTime.now().toString(),
                    views = 1
            )
            every { answerService.getAnswersByQuestionId(1) } returns listOf()

            val question = questionService.getQuestion(questionId = questionId)
            assertNotNull(question)
            assertEquals("what is postman?", question.questionTitle)
            assertEquals(1, question.views)
            assertEquals(0, question.totalAnswers)

            verifySequence {
                questionRepository.findByQuestionId(question.questionId!!)
                questionRepository.save<Question>(any())
                answerService.getAnswersByQuestionId(1)
            }
        }
    }

    @Nested
    inner class UpdateQuestion {
        @Test
        fun `update the question with empty title should not work`() {
            val questionRequestPayload = QuestionRequestPayload(questionTitle = "")
            every { questionRepository.findByQuestionId(questionId) } returns questions()[0]

            assertThrows<InvalidParameterException> {
                questionService.updateQuestion(questionId = questionId, questionRequestPayload = questionRequestPayload)
            }
        }

        @Test
        fun `update the question with empty description should not work`() {
            val questionRequestPayload = QuestionRequestPayload(questionDescription = "")
            every { questionRepository.findByQuestionId(questionId) } returns questions()[0]

            assertThrows<InvalidParameterException> {
                questionService.updateQuestion(questionId = questionId, questionRequestPayload = questionRequestPayload)
            }
        }
    }

    @Nested
    inner class DeleteQuestion {
        @Test
        fun `delete a question should work`() {
            every { questionRepository.findByQuestionId(questionId) } returns questions()[0]
            every { answerService.deleteAnswersByQuestionId(questionId = 1) } just Runs
            every { questionRepository.delete(any()) } just Runs

            questionService.deleteQuestion(questionId = questionId)

            verifySequence {
                questionRepository.findByQuestionId(questionId)
                answerService.deleteAnswersByQuestionId(questionId = 1)
                questionRepository.delete(any())
            }
        }
    }

    private fun createQuestion(): QuestionDto {
        val question = questions()[0]
        every { questionRepository.save<Question>(any())} returns question
        return questionService.createQuestion(questionRequestPayload = QuestionRequestPayload(
            questionTitle = question.questionTitle,
            questionDescription = question.questionDescription
        ))
    }

    private fun questions(): List<Question> {
        return listOf(
                Question(
                id = 1,
                questionId = questionId,
                questionTitle = "what is postman?",
                questionDescription = "why and how postman is useful?",
                askedAt = LocalDateTime.now().toString()
                ),
                Question(
                        id = 2,
                        questionId = "2$questionId",
                        questionTitle = "question two",
                        questionDescription = "description two",
                        askedAt = LocalDateTime.now().toString()
                )
        )
    }
}
