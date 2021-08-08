package com.practice.redditclone.security

import com.practice.redditclone.exceptions.SpringRedditException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.parserBuilder
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.security.*
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@Service
class JwtProvider(
    @Value("\${jwt.expiration.time}") val jwtExpirationInMillis: Long,
    @Value("\${reddit-clone.jks.key.store.password}") private val keyStorePassword: String,
    @Value("\${reddit-clone.jks.key.entry.password}") private val keyEntryPassword: String
) {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    lateinit var keyStore: KeyStore

    @PostConstruct
    fun init() {
        try {
            keyStore = KeyStore.getInstance("JKS")
            val resourceAsStream = javaClass.getResourceAsStream("/reddit-clone.jks")
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray())
        } catch (e: Exception) {
            logger.error { e }
            throw SpringRedditException("Exception occurred while loading keystore", e)
        }
    }

    fun generateToken(authentication: Authentication): String {
        val principal: User = authentication.principal as User
        return Jwts.builder()
            .setSubject(principal.username)
            .signWith(getPrivateKey())
            .setExpiration(
                Date.from(
                    Instant.now().plusMillis(jwtExpirationInMillis)
                )
            )
            .compact()
    }

    fun generateTokenWithUsername(username: String) =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .signWith(getPrivateKey())
            .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
            .compact()

    private fun getPrivateKey(): PrivateKey {
        return try {
            keyStore.getKey("reddit-clone", keyEntryPassword.toCharArray()) as PrivateKey
        } catch (e: Exception) {
            logger.error { e }
            throw SpringRedditException("Exception occurred while retrieving public key from keystore")
        }
    }

    fun validateToken(jwt: String): Boolean {
        parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt)
        return true
    }

    private fun getPublicKey(): PublicKey =
        try {
            keyStore.getCertificate("reddit-clone").publicKey
        } catch (e: KeyStoreException) {
            log.error { e }
            throw SpringRedditException("Exception occured while retrieving public key", e)
        }

    fun getUsernameFromJwt(token: String) =
        parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(token).body.subject

}