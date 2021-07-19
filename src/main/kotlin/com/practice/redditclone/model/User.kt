package com.practice.redditclone.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @CreationTimestamp
    @Column(name = "created", updatable = false, nullable = false)
    val created: Instant = Instant.now()

    @UpdateTimestamp
    @Column(name = "modified", nullable = false)
    var modified: Instant = Instant.now()

    @NotBlank(message = "Username is required")
    @Column(name = "username")
    lateinit var username: String

    @NotBlank(message = "Password is required")
    @Column(name = "password")
    lateinit var password: String

    @Email
    @NotBlank(message = "Email is required")
    @Column(name = "email")
    lateinit var email: String

    @Column(name = "enabled")
    var enabled: Boolean = false
}