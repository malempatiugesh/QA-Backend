package com.ugesh.qa.payloads

import com.ugesh.qa.dtos.QuestionDto


data class QuestionResponsePayload(
        val questions: List<QuestionDto>? = null
)
