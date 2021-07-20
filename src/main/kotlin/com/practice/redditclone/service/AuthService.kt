package com.practice.redditclone.service

import com.practice.redditclone.dto.RegisterRequest
import com.practice.redditclone.exceptions.SpringRedditException
import com.practice.redditclone.model.NotificationEmail
import com.practice.redditclone.model.User
import com.practice.redditclone.model.VerificationToken
import com.practice.redditclone.repository.UserRepository
import com.practice.redditclone.repository.VerificationTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val verificationTokenRepository: VerificationTokenRepository,
    private val mailService: MailService
) {

    @Transactional
    fun signup(registerRequest : RegisterRequest){
        val user : User = User()
            .apply {
                username = registerRequest.username
                email = registerRequest.email
                password = passwordEncoder.encode(registerRequest.password)
            }

        userRepository.save(user)
        val token = generateVerificationToken(user)
        mailService.sendMail(NotificationEmail("Please Activate your Account",
            user.email, "Thank you for signing up to Spring Reddit, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:8080/api/auth/accountVerification/" + token))
    }

    private fun generateVerificationToken(user: User): String {
        val token = UUID.randomUUID().toString()
        // We save the verification token and user in the DB to allow verification later in time
        val verificationToken = VerificationToken()
        verificationToken.apply {
            this.token = token
            this.user = user
        }
        verificationTokenRepository.save(verificationToken)
        return token
    }

    fun verifyAccount(token: String) {
        val verificationToken : VerificationToken = verificationTokenRepository
            .findByToken(token) ?: throw SpringRedditException("Invalid token")

        // Query the user with this token and enable it
        fetchAndEnableUser(verificationToken)
    }

    @Transactional
    private fun fetchAndEnableUser(verificationToken: VerificationToken) {
        val username : String = verificationToken.user.username
        val user : User = userRepository
            .findByUsername(username) ?: throw SpringRedditException("User with name $username not found")
        user.enabled = true
        userRepository.save(user)
    }

}