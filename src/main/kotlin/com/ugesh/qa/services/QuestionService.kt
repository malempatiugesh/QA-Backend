package com.ugesh.qa.services

import com.ugesh.qa.models.Question
import com.ugesh.qa.payloads.QuestionRequestPayload
import com.ugesh.qa.payloads.QuestionResponsePayload
import com.ugesh.qa.repositories.QuestionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class QuestionService(private val questionRepository: QuestionRepository) {
    fun createQuestion(questionRequestPayload: QuestionRequestPayload): QuestionResponsePayload {
      val question = Question(
        questionId = UUID.randomUUID().toString().replace("-", ""),
        questionTitle = questionRequestPayload.questionTitle,
        questionDescription = questionRequestPayload.questionDescription,
        askedAt = LocalDateTime.now().toString()
      )
      questionRepository.save(question)
      return QuestionResponsePayload.toQuestionResponse(question = question)
    }
}
