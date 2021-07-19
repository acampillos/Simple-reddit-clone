package com.practice.redditclone.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.lang.Nullable
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "posts")
class Post {

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

        @NotBlank(message = "Post Name cannot be empty")
        @Column(name = "post_name")
        lateinit var postName: String

        @Column(name = "url")
        lateinit var url: String

        @Lob
        @Column(name = "description")
        lateinit var description: String

        @Column(name = "vote_count")
        var voteCount: Long = 0

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        lateinit var user: User

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "subreddit_id")
        lateinit var subreddit: Subreddit

}