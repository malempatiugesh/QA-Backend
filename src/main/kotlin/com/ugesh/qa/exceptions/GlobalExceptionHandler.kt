package com.ugesh.qa.exceptions

import com.ugesh.qa.exceptions.notfound.AnswerNotFoundException
import com.ugesh.qa.exceptions.notfound.QuestionNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun questionNotFoundException(exception: QuestionNotFoundException): ResponseEntity<ExceptionResponse> {
        val error = ExceptionResponse(status = HttpStatus.BAD_REQUEST.value(), message = exception.message)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun answerNotFoundException(exception: AnswerNotFoundException): ResponseEntity<ExceptionResponse> {
        val error = ExceptionResponse(status = HttpStatus.BAD_REQUEST.value(), message = exception.message)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun invalidParameterException(exception: InvalidParameterException): ResponseEntity<ExceptionResponse> {
        val error = ExceptionResponse(status = HttpStatus.BAD_REQUEST.value(), message = exception.message)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleException(exception: Exception): ResponseEntity<ExceptionResponse> {
        val error = ExceptionResponse(status = HttpStatus.BAD_REQUEST.value(), message = exception.message)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}
