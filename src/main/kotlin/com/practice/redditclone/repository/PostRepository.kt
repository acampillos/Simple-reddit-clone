package com.practice.redditclone.repository

import com.practice.redditclone.model.Post
import com.practice.redditclone.model.Subreddit
import com.practice.redditclone.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    fun findAllBySubreddit(subreddit: Subreddit): List<Post>
    fun findByUser(user: User): List<Post>

}