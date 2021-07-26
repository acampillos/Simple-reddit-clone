package com.practice.redditclone.dto

data class AuthenticationResponse(
    val authenticationToken : String,
    val username : String
)