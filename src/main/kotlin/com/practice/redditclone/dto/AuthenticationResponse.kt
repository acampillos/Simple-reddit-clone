package com.practice.redditclone.dto

import java.time.Instant

data class AuthenticationResponse(
    val authenticationToken: String,
    val refreshToken: String,
    val expiresAt: Instant,
    val username: String
)