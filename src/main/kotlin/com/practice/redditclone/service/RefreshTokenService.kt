package com.practice.redditclone.service

import com.practice.redditclone.exceptions.SpringRedditException
import com.practice.redditclone.model.RefreshToken
import com.practice.redditclone.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun generateRefreshToken(): RefreshToken =
        refreshTokenRepository.save(RefreshToken().apply { token = UUID.randomUUID().toString() })

    fun validateRefreshToken(token: String) {
        refreshTokenRepository.findByToken(token)
            ?: throw SpringRedditException("Invalid refresh token")
    }

    fun deleteRefreshToken(token: String) {
        refreshTokenRepository.deleteByToken(token)
    }



}