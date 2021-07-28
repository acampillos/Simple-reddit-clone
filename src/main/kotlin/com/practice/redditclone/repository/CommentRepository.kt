package com.practice.redditclone.repository

import com.practice.redditclone.model.Comment
import com.practice.redditclone.model.Post
import com.practice.redditclone.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    fun findByPost(post: Post): List<Comment>
    fun findAllByUser(user: User): List<Comment>

}