package com.practice.redditclone.controller

import com.practice.redditclone.dto.RegisterRequest
import com.practice.redditclone.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController (
    private val authService: AuthService
        ){

    @PostMapping("/signup")
    fun signup(@RequestBody registerRequestBody: RegisterRequest) : ResponseEntity<String>{
        // We will get user details as part of the request body
        authService.signup(registerRequestBody)
        return ResponseEntity<String>("User Registration Successful", HttpStatus.OK)
    }

}