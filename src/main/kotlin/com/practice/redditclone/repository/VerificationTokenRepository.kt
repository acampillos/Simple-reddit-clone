package com.practice.redditclone.repository

import com.practice.redditclone.model.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): VerificationToken?
    fun findByUserId(userId: Long): VerificationToken?
}