package com.practice.redditclone.mapper

import com.practice.redditclone.dto.CommentDto
import com.practice.redditclone.model.Comment
import com.practice.redditclone.model.Post
import com.practice.redditclone.model.User
import org.springframework.stereotype.Component

@Component
class CommentMapper {

    fun toDto(comment: Comment): CommentDto =
        CommentDto(
            checkNotNull(comment.id),
            checkNotNull(comment.post.id),
            comment.created,
            comment.text,
            comment.user.username
        )

    fun toEntity(commentDto: CommentDto, post: Post, user: User): Comment =
        Comment().apply {
            this.post = post
            this.user = user
            this.text = commentDto.text
        }

}