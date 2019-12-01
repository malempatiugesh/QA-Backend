package com.ugesh.qa.repositories

import com.ugesh.qa.models.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository: JpaRepository<Question, Long> {
    fun findByQuestionId(questionId: String): Question
}
