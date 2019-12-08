package com.ugesh.qa.repositories

import com.ugesh.qa.models.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository : JpaRepository<Answer, Long> {
    fun findByAnswerId(answerId: String): Answer
    fun findByQuestionId(questionId: Long?): List<Answer>
    fun deleteByQuestionId(questionId: Long?)
}
