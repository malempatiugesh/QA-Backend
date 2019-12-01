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
        return questions.map { QuestionDto.toDto(it) }
    }

    fun getQuestion(questionId: String): QuestionDto {
        val foundQuestion = questionRepository.findByQuestionId(questionId = questionId)
        return if(foundQuestion.questionId == questionId)
            QuestionDto.toDto(foundQuestion)
        else
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
    }

    private fun checkQuestionTitle(title: String?): String {
        return if (!title.isNullOrBlank()) title else throw InvalidParameterException("Question title should not be null")
    }
}
