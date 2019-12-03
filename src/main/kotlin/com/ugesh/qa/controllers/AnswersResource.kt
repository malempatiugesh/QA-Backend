package com.ugesh.qa.controllers

import com.ugesh.qa.dtos.AnswerDto
import com.ugesh.qa.dtos.payloads.answers.AnswerPayload
import com.ugesh.qa.services.AnswerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AnswersResource(private val answerService: AnswerService) {
    @PostMapping("/api/questions/{questionId}/answers")
    fun createAnswer(
        @PathVariable(value = "questionId") questionId: String,
        @RequestBody answer: AnswerPayload
    ): ResponseEntity<AnswerDto> {
        val createdAnswer = answerService.createAnswer(questionId = questionId, answerPayload = answer)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer)
    }
}
