package com.ugesh.qa.services

import com.ugesh.qa.dtos.AnswerDto
import com.ugesh.qa.dtos.payloads.answers.AnswerPayload
import com.ugesh.qa.exceptions.InvalidParameterException
import com.ugesh.qa.exceptions.questions.QuestionNotFoundException
import com.ugesh.qa.models.Answer
import com.ugesh.qa.models.Question
import com.ugesh.qa.repositories.AnswerRepository
import com.ugesh.qa.repositories.QuestionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AnswerService(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository
) {
    fun createAnswer(questionId: String, answerPayload: AnswerPayload): AnswerDto {
        val answer = Answer(
            answerId = UUID.randomUUID().toString().replace("-", ""),
            answer = checkAnswer(answer = answerPayload.answer),
            question = setQuestion(questionId = questionId)
        )
        answerRepository.save(answer)
       return AnswerDto.toDto(answerData = answer)
    }

    private fun setQuestion(questionId: String): Question {
        val foundQuestion = questionRepository.findByQuestionId(questionId = questionId)
        if(foundQuestion.questionId != questionId) {
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
        }
        return foundQuestion
    }

    private fun checkAnswer(answer: String?): String {
        return if (!answer.isNullOrBlank()) answer else throw InvalidParameterException("Answer should not be null or empty")
    }
}
