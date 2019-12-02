package com.ugesh.qa.services

import com.ugesh.qa.dtos.QuestionDto
import com.ugesh.qa.exceptions.InvalidParameterException
import com.ugesh.qa.exceptions.questions.QuestionNotFoundException
import com.ugesh.qa.models.Question
import com.ugesh.qa.payloads.QuestionRequestPayload
import com.ugesh.qa.payloads.QuestionResponsePayload
import com.ugesh.qa.repositories.QuestionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class QuestionService(private val questionRepository: QuestionRepository) {
    fun createQuestion(questionRequestPayload: QuestionRequestPayload): QuestionDto {
      val question = Question(
        questionId = UUID.randomUUID().toString().replace("-", ""),
        questionTitle = checkQuestionTitle(title = questionRequestPayload.questionTitle),
        questionDescription = questionRequestPayload.questionDescription,
        askedAt = LocalDateTime.now().toString()
      )
      questionRepository.save(question)
      return QuestionDto.toDto(question = question)
    }

    fun getQuestions(): List<QuestionDto>  {
        val questions = questionRepository.findAll()
        return questions.map { QuestionDto.toDto(question = it) }
    }

    fun getQuestion(questionId: String): QuestionDto {
        val foundQuestion = questionRepository.findByQuestionId(questionId = questionId)
        if(foundQuestion.questionId != questionId) {
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
        }
        foundQuestion.views++
        questionRepository.save(foundQuestion)
        return QuestionDto.toDto(question = foundQuestion)
    }

    fun updateQuestion(questionId: String, questionRequestPayload: QuestionRequestPayload): QuestionDto {
        val questionToUpdate = questionRepository.findByQuestionId(questionId = questionId)
        if (questionToUpdate.questionId != questionId) {
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
        }
        questionRequestPayload.questionTitle?.let { questionToUpdate.questionTitle = it }
        questionRequestPayload.questionDescription?.let { questionToUpdate.questionDescription = it }
        questionRepository.save(questionToUpdate)
        return QuestionDto.toDto(question = questionToUpdate)
    }

    fun deleteQuestion(questionId: String) {
        val questionToRemove = questionRepository.findByQuestionId(questionId = questionId)
        if (questionToRemove.questionId != questionId) {
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
        }
        questionRepository.delete(questionToRemove)
    }

    private fun checkQuestionTitle(title: String?): String {
        return if (!title.isNullOrBlank()) title else throw InvalidParameterException("Question title should not be null")
    }
}
