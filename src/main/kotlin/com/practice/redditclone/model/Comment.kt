package com.practice.redditclone.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "comments")
class Comment {

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

    @NotEmpty
    @Column(name = "text")
    lateinit var text: String

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    lateinit var post: Post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    lateinit var user: User

}
