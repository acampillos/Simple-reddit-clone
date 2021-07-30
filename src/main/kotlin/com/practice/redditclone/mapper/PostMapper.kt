package com.practice.redditclone.mapper

import com.github.marlonlom.utilities.timeago.TimeAgo
import com.practice.redditclone.dto.PostRequest
import com.practice.redditclone.dto.PostResponse
import com.practice.redditclone.model.Post
import com.practice.redditclone.model.Subreddit
import com.practice.redditclone.model.User
import com.practice.redditclone.repository.CommentRepository
import org.springframework.stereotype.Component

@Component
class PostMapper(
    private val commentRepository: CommentRepository
) {

    fun toEntity(postRequest: PostRequest, subreddit: Subreddit, user: User) =
        Post().apply {
            this.subreddit = subreddit
            this.user = user
            this.description = postRequest.description
            this.url = postRequest.url
            this.postName = postRequest.postName
        }

    fun toPostResponse(post: Post): PostResponse =
        PostResponse(
            checkNotNull(post.id),
            post.postName,
            post.url,
            post.description,
            post.user.username,
            checkNotNull(post.subreddit.name),
            post.voteCount,
            commentRepository.findByPost(post).size,
            TimeAgo.using(post.created.toEpochMilli())
        )

}