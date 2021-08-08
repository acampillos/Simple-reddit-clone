package com.practice.redditclone.controller

import com.practice.redditclone.dto.AuthenticationResponse
import com.practice.redditclone.dto.LoginRequest
import com.practice.redditclone.dto.RefreshTokenRequest
import com.practice.redditclone.dto.RegisterRequest
import com.practice.redditclone.service.AuthService
import com.practice.redditclone.service.RefreshTokenService
import com.sun.mail.iap.Response
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController (
    private val authService: AuthService,
    private val refreshTokenService: RefreshTokenService
){

    @PostMapping("/signup")
    fun signup(@RequestBody registerRequestBody: RegisterRequest) : ResponseEntity<String>{
        // We will get user details as part of the request body
        authService.signup(registerRequestBody)
        return ResponseEntity<String>("User Registration Successful", HttpStatus.OK)
    }

    @GetMapping("accountVerification/{token}")
    fun verifyAccount(@PathVariable token : String) : ResponseEntity<String> {
        authService.verifyAccount(token)
        return ResponseEntity<String>("Account Activated Successfully", HttpStatus.OK)
    }

    @PostMapping(
        value = ["/login"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun login(@RequestBody loginRequest : LoginRequest): AuthenticationResponse {
        return authService.login(loginRequest)
    }

    @PostMapping("/refresh/token")
    fun refreshToken(@RequestBody refreshTokenRequest: @Valid RefreshTokenRequest): AuthenticationResponse =
        authService.refreshToken(refreshTokenRequest)

    @PostMapping("/logout")
    fun logout(@RequestBody refreshTokenRequest: @Valid RefreshTokenRequest): ResponseEntity<String> =
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.refreshToken)
            .let {
                ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted successfully!")
            }

}