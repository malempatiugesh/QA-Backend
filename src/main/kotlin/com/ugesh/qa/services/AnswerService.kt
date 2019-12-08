package com.ugesh.qa.services

import com.ugesh.qa.dtos.AnswerDto
import com.ugesh.qa.dtos.payloads.answers.AnswerRequestPayload
import com.ugesh.qa.exceptions.InvalidParameterException
import com.ugesh.qa.exceptions.notfound.AnswerNotFoundException
import com.ugesh.qa.exceptions.notfound.QuestionNotFoundException
import com.ugesh.qa.models.Answer
import com.ugesh.qa.models.Question
import com.ugesh.qa.repositories.AnswerRepository
import com.ugesh.qa.repositories.QuestionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class AnswerService(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository
) {
    fun createAnswer(questionId: String, answerRequestPayload: AnswerRequestPayload): AnswerDto {
        val answer = Answer(
            answerId = UUID.randomUUID().toString().replace("-", ""),
            answer = checkAnswer(answer = answerRequestPayload.answer),
            answeredAt = LocalDateTime.now().toString(),
            question = setQuestion(questionId = questionId)
        )
        answerRepository.save(answer)
       return AnswerDto.toDto(answerData = answer)
    }

    fun getAnswersByQuestionId(questionId: Long?): List<AnswerDto> {
          return answerRepository.findByQuestionId(questionId = questionId).map {AnswerDto.toDto(it)}
    }

    fun deleteAnswersByQuestionId(questionId: Long?) {
        answerRepository.deleteByQuestionId(questionId = questionId)
    }

    fun updateAnswer(answerId: String, answerRequestPayload: AnswerRequestPayload): AnswerDto {
        val answerToUpdate = answerRepository.findByAnswerId(answerId = answerId)
        if(answerToUpdate.answerId != answerId) {
            throw AnswerNotFoundException("Answer is not available for the given id: $answerId")
        }
        answerToUpdate.answer = checkAnswer(answerRequestPayload.answer)
        answerRepository.save(answerToUpdate)
        return AnswerDto.toDto(answerData = answerToUpdate)
    }

    fun deleteAnswer(answerId: String) {
        val answerToDelete = answerRepository.findByAnswerId(answerId = answerId)
        if(answerToDelete.answerId != answerId) throw AnswerNotFoundException("Answer is not available for the given id: $answerId")
        if (answerToDelete.question.answers > 0) {
            answerToDelete.question.answers--
        }
        answerRepository.delete(answerToDelete)
    }

    private fun setQuestion(questionId: String): Question {
        val foundQuestion = questionRepository.findByQuestionId(questionId = questionId)
        if(foundQuestion.questionId != questionId) {
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
        }
        foundQuestion.answers++
        questionRepository.save(foundQuestion)
        return foundQuestion
    }

    private fun checkAnswer(answer: String?): String {
        return if (!answer.isNullOrBlank()) answer else throw InvalidParameterException("Answer should not be null or empty")
    }
}
