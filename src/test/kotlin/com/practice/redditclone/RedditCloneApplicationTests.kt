package com.practice.redditclone

import com.practice.redditclone.config.SecurityConfig
import com.practice.redditclone.dto.AuthenticationResponse
import com.practice.redditclone.dto.LoginRequest
import com.practice.redditclone.dto.RegisterRequest
import com.practice.redditclone.dto.SubredditDto
import com.practice.redditclone.model.VerificationToken
import com.practice.redditclone.repository.UserRepository
import com.practice.redditclone.repository.VerificationTokenRepository
import com.practice.redditclone.service.AuthService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedditCloneApplicationTests {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var verificationTokenRepository: VerificationTokenRepository

    @Autowired
    lateinit var securityConfig: SecurityConfig

    @Autowired
    lateinit var authService: AuthService

    @LocalServerPort
    var port: Int = 0

    @Test
    fun contextLoads() {
    }

    @Test
    fun `An user can sign up`() {
        testRestTemplate
            .postForEntity(
                "http://localhost:$port/api/auth/signup",
                RegisterRequest(
                    "test@reddit-clone.com",
                    "test",
                    "test"
                ),
                String::class.java
            ).let {
                assertEquals(HttpStatus.OK, it.statusCode)
                assertEquals("User Registration Successful", it.body)
            }

        userRepository
            .findByUsername("test")
            ?.let {
                assertEquals(it.email, "test@reddit-clone.com")
                assertEquals(it.username, "test")
                assertTrue(
                    securityConfig
                        .passwordEncoder().matches(
                            "test",
                            it.password
                        )
                )
            }
    }

    @Test
    fun `An user can be verified`() {
        val token: VerificationToken? = verificationTokenRepository
            .findByUserId(1L)

        testRestTemplate
            .getForEntity(
                "http://localhost:$port/api/auth/accountVerification/${token?.token}",
                String::class.java
            ).let {
                assertEquals(HttpStatus.OK, it.statusCode)
                assertEquals("Account Activated Successfully", it.body)
            }

    }

    @Test
    fun `An user can log in`() {
        val token: VerificationToken? = verificationTokenRepository
            .findByUserId(1L)
        authService.verifyAccount(token?.token ?: throw RuntimeException())

        testRestTemplate
            .postForEntity(
                "http://localhost:$port/api/auth/login",
                LoginRequest(
                    "test",
                    "test"
                ),
                AuthenticationResponse::class.java
            )
            .let {
                assertEquals(HttpStatus.OK, it.statusCode)
                assertEquals("test", it.body?.username)

            }
    }


}
