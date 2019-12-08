package com.ugesh.qa.models

import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = Answer.TABLE_NAME)
class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotBlank
    @Column(name = "answer_id")
    var answerId: String,

    @NotBlank
    @Lob
    @Column(name = "answer", length = 10000)
    var answer: String,

    @NotBlank
    @Column(name="answered_at")
    var answeredAt: String,

    @NotBlank
    @Column(name = "votes")
    var votes: Int = 0,

    @ManyToOne
    @JoinColumn(name = "question_id")
    var question: Question

): Serializable {
    companion object {
        const val TABLE_NAME = "answers"
    }
}
