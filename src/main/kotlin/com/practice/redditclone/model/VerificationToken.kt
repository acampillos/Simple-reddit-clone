package com.practice.redditclone.model

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "verification_tokens")
class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "token")
    lateinit var token: String

    @OneToOne(fetch = FetchType.LAZY)
    lateinit var user: User

    @Column(name = "expiry_date")
    var expiryDate: Instant? = null

}