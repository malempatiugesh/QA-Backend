package com.ugesh.qa.dtos

import com.ugesh.qa.models.Answer

data class AnswerDto(
    var answerId: String? = null,
    var answer: String? = null,
    var questionId: String? = null
) {
  companion object {
      fun toDto(answerData: Answer): AnswerDto {
          return with(answerData) {
              AnswerDto(
                  answerId = answerId,
                  answer = answer,
                  questionId = question.questionId
              )
          }
      }
  }
}
