package com.ugesh.qa.controllers

import com.ugesh.qa.payloads.QuestionRequestPayload
import com.ugesh.qa.payloads.QuestionResponsePayload
import com.ugesh.qa.services.QuestionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(QuestionsResource.QuestionsResource_PATH)
class QuestionsResource(private val questionService: QuestionService) {
    companion object{
        const val QuestionsResource_PATH = "/api/questions"
    }

    @PostMapping
    fun createQuestion(@RequestBody questionRequestPayload: QuestionRequestPayload): ResponseEntity<QuestionResponsePayload> {
        val createdQuestion = questionService.createQuestion(questionRequestPayload = questionRequestPayload)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion)
    }
}
