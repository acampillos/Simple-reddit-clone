package com.practice.redditclone.model

import javax.persistence.*

@Entity
@Table(name = "votes")
class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type")
    lateinit var voteType: VoteType

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    lateinit var post: Post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    lateinit var user: User

}