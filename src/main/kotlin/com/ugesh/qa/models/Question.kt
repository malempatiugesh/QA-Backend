package com.ugesh.qa.models

import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = Question.TABLE_NAME)
data class Question(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @NotBlank
        var questionId: String,

        @NotBlank
        var questionTitle: String,

        @NotBlank
        var questionDescription: String,

        @NotBlank
        var askedAt: String,

        var views: Int = 0,

        var votes: Int = 0,

        var answers: Int = 0

): Serializable {
    companion object{
        const val TABLE_NAME = "questions"
    }
}
