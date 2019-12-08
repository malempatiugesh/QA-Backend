package com.ugesh.qa.controllers

import com.ugesh.qa.dtos.AnswerDto
import com.ugesh.qa.dtos.payloads.answers.AnswerRequestPayload
import com.ugesh.qa.services.AnswerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AnswersResource(private val answerService: AnswerService) {
    @PostMapping("/api/questions/{questionId}/answers")
    fun createAnswer(
        @PathVariable(value = "questionId") questionId: String,
        @RequestBody answerRequest: AnswerRequestPayload
    ): ResponseEntity<AnswerDto> {
        val createdAnswer = answerService.createAnswer(questionId = questionId, answerRequestPayload = answerRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer)
    }

    @PutMapping("/api/questions/{questionId}/answers/{answerId}")
    fun updateAnswer(
        @PathVariable(value = "answerId") answerId: String,
        @RequestBody answerRequest: AnswerRequestPayload
    ): ResponseEntity<AnswerDto> {
        val updatedAnswer = answerService.updateAnswer(answerId = answerId, answerRequestPayload = answerRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedAnswer)
    }

    @DeleteMapping("/api/questions/{questionId}/answers/{answerId}")
    fun deleteAnswer(@PathVariable(value = "answerId") answerId: String): ResponseEntity<Any> {
        answerService.deleteAnswer(answerId = answerId)
        return ResponseEntity.ok().build()
    }
}
