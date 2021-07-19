package com.practice.redditclone.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "subreddits")
class Subreddit {

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

    @NotBlank(message = "Community name is required")
    @Column(name = "name")
    lateinit var name: String

    @NotBlank(message = "Description is required")
    @Column(name = "description")
    lateinit var description: String

    @OneToMany(
            mappedBy = "subreddit",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = [CascadeType.ALL]
    )
    var posts: MutableList<Post> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var user: User

}