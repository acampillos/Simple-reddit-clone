package com.practice.redditclone.repository

import com.practice.redditclone.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {

    fun findByToken(token: String): RefreshToken?
    fun deleteByToken(token: String)

}