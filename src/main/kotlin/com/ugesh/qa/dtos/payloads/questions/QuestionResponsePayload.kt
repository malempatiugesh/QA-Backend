package com.ugesh.qa.dtos.payloads.questions

import com.ugesh.qa.dtos.QuestionDto


data class QuestionResponsePayload(
    val total: Int = 0,
    val questions: List<QuestionDto> = emptyList()
)
