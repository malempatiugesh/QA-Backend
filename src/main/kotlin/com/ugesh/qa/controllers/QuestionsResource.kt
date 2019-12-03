package com.ugesh.qa.controllers

import com.ugesh.qa.dtos.QuestionDto
import com.ugesh.qa.dtos.payloads.QuestionRequestPayload
import com.ugesh.qa.dtos.payloads.QuestionResponsePayload
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
        return ResponseEntity.status(HttpStatus.CREATED).body(QuestionResponsePayload(questions = listOf(createdQuestion)))
    }

    //TODO: apply pagination and sorting (recent to old)
    @GetMapping
    fun getQuestions(): ResponseEntity<QuestionResponsePayload> {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestions())
    }

    @GetMapping("/{id}")
    fun getQuestion(@PathVariable(value = "id") questionId: String): ResponseEntity<QuestionDto> {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestion(questionId = questionId))
    }

    //TODO: implement logic to update votes
    @PutMapping("/{id}")
    fun updateQuestion(
        @PathVariable(value = "id") questionId: String,
        @RequestBody questionRequestPayload: QuestionRequestPayload
    ): ResponseEntity<QuestionDto> {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questionService.updateQuestion(
                        questionId = questionId,
                        questionRequestPayload = questionRequestPayload
                )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteQuestion(@PathVariable("id") questionId: String): ResponseEntity<Any> {
        questionService.deleteQuestion(questionId = questionId)
        return ResponseEntity.ok().build()
    }
}
