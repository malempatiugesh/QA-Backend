package com.ugesh.qa.dtos

import com.ugesh.qa.models.Question

data class QuestionDto(
        var questionId: String? = null,
        var questionTitle: String? = null,
        var questionDescription: String? = null,
        var askedAt: String? = null,
        var views: Int = 0,
        var votes: Int = 0,
        var answers: Int = 0
) {
    companion object {
        fun toDto(question: Question): QuestionDto {
            return with(question) {
                QuestionDto(
                        questionId = questionId,
                        questionTitle = questionTitle,
                        questionDescription = questionDescription,
                        askedAt = askedAt,
                        views = views,
                        votes = votes,
                        answers = answers
                )
            }
        }
    }
}
