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
        @Column(name = "question_id")
        var questionId: String,

        @NotBlank
        @Column(name = "question_title")
        var questionTitle: String,

        @NotBlank
        @Lob
        @Column(name = "question_description", length=10000)
        var questionDescription: String,

        @NotBlank
        @Column(name = "asked_at")
        var askedAt: String,

        @NotBlank
        var views: Int = 0,

        @NotBlank
        var votes: Int = 0,

        // mappedBy referes to the question property in the Answer entity class
        //@OneToMany(mappedBy = "question", cascade = [CascadeType.ALL])
        @NotBlank
        var answers: Int = 0
): Serializable {
    companion object{
        const val TABLE_NAME = "questions"
    }
}
