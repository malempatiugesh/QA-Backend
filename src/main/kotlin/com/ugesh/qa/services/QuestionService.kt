package com.ugesh.qa.services

import com.ugesh.qa.dtos.QuestionDto
import com.ugesh.qa.exceptions.InvalidParameterException
import com.ugesh.qa.exceptions.questions.QuestionNotFoundException
import com.ugesh.qa.models.Question
import com.ugesh.qa.dtos.payloads.questions.QuestionRequestPayload
import com.ugesh.qa.dtos.payloads.questions.QuestionResponsePayload
import com.ugesh.qa.repositories.QuestionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Service
class QuestionService(
        private val questionRepository: QuestionRepository,
        private val answerService: AnswerService
) {
    fun createQuestion(questionRequestPayload: QuestionRequestPayload): QuestionDto {
      val question = Question(
        questionId = UUID.randomUUID().toString().replace("-", ""),
        questionTitle = checkQuestionTitle(title = questionRequestPayload.questionTitle),
        questionDescription = checkQuestionDescription(questionRequestPayload.questionDescription),
        askedAt = LocalDateTime.now().toString()
      )
      questionRepository.save(question)
      return QuestionDto.toDto(question = question)
    }

    fun getQuestions(): QuestionResponsePayload {
        val questions = questionRepository.findAll()
        return QuestionResponsePayload(
                questions = if (questions.isNotEmpty()) questions.map { QuestionDto.toDto(question = it) } else emptyList(),
                total = questions.size
        )
    }

    fun getQuestion(questionId: String): QuestionDto {
        val foundQuestion = questionRepository.findByQuestionId(questionId = questionId)
        if(foundQuestion.questionId != questionId) {
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
        }
        foundQuestion.views++
        questionRepository.save(foundQuestion)
        return QuestionDto.toDto(
                question = foundQuestion,
                answersToQuestion = answerService.getAnswersByQuestionId(questionId = foundQuestion.id)
        )
    }

    fun updateQuestion(questionId: String, questionRequestPayload: QuestionRequestPayload): QuestionDto {
        val questionToUpdate = questionRepository.findByQuestionId(questionId = questionId)
        if (questionToUpdate.questionId != questionId) {
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
        }
        questionRequestPayload.questionTitle?.let {
            questionToUpdate.questionTitle = checkForEmpty(text = it, type = "title")
        }
        questionRequestPayload.questionDescription?.let {
            questionToUpdate.questionDescription = checkForEmpty(text = it, type = "description")
        }
        questionRepository.save(questionToUpdate)
        return QuestionDto.toDto(
                question = questionToUpdate,
                answersToQuestion = answerService.getAnswersByQuestionId(questionId = questionToUpdate.id)
        )
    }

    @Transactional
    fun deleteQuestion(questionId: String) {
        val questionToRemove = questionRepository.findByQuestionId(questionId = questionId)
        if (questionToRemove.questionId != questionId) {
            throw QuestionNotFoundException("Question is not available for the given id: $questionId")
        }
        answerService.deleteAnswersByQuestionId(questionId = questionToRemove.id)
        questionRepository.delete(questionToRemove)
    }

    private fun checkQuestionTitle(title: String?): String {
        return if (!title.isNullOrBlank()) title else throw InvalidParameterException("Question title should not be null or empty")
    }

    private fun checkQuestionDescription(description: String?): String {
        return if (!description.isNullOrBlank()) description else throw InvalidParameterException("Question description should not be null or empty")
    }

    private fun checkForEmpty(text: String, type: String): String {
        return if (text.isNotBlank()) text else throw InvalidParameterException("Question $type should not be empty")
    }
}
