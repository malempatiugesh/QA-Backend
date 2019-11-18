package com.ugesh.qa.payloads

import com.ugesh.qa.models.Question

data class QuestionResponsePayload(
    var questionId: String? = null,
    var questionTitle: String? = null,
    var questionDescription: String? = null,
    var askedAt: String? = null,
    var views: Int = 0,
    var votes: Int = 0,
    var answers: Int = 0
) {
    companion object {
        fun toQuestionResponse(question: Question): QuestionResponsePayload {
            return with(question) {
                QuestionResponsePayload(
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
