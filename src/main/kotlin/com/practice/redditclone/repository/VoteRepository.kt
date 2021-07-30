package com.practice.redditclone.repository

import com.practice.redditclone.model.Post
import com.practice.redditclone.model.User
import com.practice.redditclone.model.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VoteRepository : JpaRepository<Vote, Long> {
    fun findTopByPostAndUserOrderByIdDesc(post: Post, currentUser: User): Vote?
}