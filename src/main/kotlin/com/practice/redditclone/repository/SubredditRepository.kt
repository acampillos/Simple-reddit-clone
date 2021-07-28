package com.practice.redditclone.repository

import com.practice.redditclone.model.Subreddit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubredditRepository : JpaRepository<Subreddit, Long> {

    fun findByName(subredditName: String): Subreddit
}