package com.ugesh.qa.dtos.payloads

import com.ugesh.qa.dtos.QuestionDto


data class QuestionResponsePayload(
        val questions: List<QuestionDto> = emptyList(),
        val total: Int = 0
)
