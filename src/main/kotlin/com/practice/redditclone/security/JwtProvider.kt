package com.practice.redditclone.security

import io.jsonwebtoken.Jwts
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.security.KeyStore
import java.security.PrivateKey
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@Service
//class JwtProvider(
//    @Value("\${reddit-clone.jks.key.store.password}") private val keyStorePassword: String,
//    @Value("\${reddit-clone.jks.key.entry.password}") private val keyEntryPassword: String
//) {
class JwtProvider {

    lateinit var keyStore: KeyStore

    @PostConstruct
    fun init() {
        try {
            keyStore = KeyStore.getInstance("JKS")
            val resourceAsStream = javaClass.getResourceAsStream("/reddit-clone.jks")
            keyStore.load(resourceAsStream, "password".toCharArray())
        } catch (e: Exception) {
            logger.error { e }
            throw e
        }
    }

    fun generateToken(authentication: Authentication): String {
        val principal : User = authentication.principal as User
        return Jwts.builder()
            .setSubject(principal.username)
            .signWith(getPrivateKey())
            .compact()
    }

    private fun getPrivateKey(): PrivateKey {
        return try {
            keyStore.getKey("reddit-clone", "password".toCharArray()) as PrivateKey
        } catch (e: Exception) {
            logger.error { e }
            throw RuntimeException("Exception occurred while retrieving public key from keystore")
        }
    }

}