package com.practice.redditclone.service

import com.practice.redditclone.dto.VoteDto
import com.practice.redditclone.exceptions.PostNotFoundException
import com.practice.redditclone.exceptions.SpringRedditException
import com.practice.redditclone.model.Post
import com.practice.redditclone.model.Vote
import com.practice.redditclone.model.VoteType
import com.practice.redditclone.repository.PostRepository
import com.practice.redditclone.repository.VoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VoteService(
    private val voteRepository: VoteRepository,
    private val postRepository: PostRepository,
    private val authService: AuthService
) {


    @Transactional
    fun vote(voteDto: VoteDto) {
        val post: Post = postRepository.findById(voteDto.postId)
            .orElseThrow { PostNotFoundException("Post not found with ID $voteDto.postId") }
        val voteByPostAndUser: Vote? = voteRepository
            .findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser())

        voteByPostAndUser?.let {
            if (it.voteType == voteDto.voteType) {
                throw SpringRedditException("You have already ${voteDto.voteType}'d for this post")
            }
        }

        if (voteDto.voteType == VoteType.UPVOTE) {
            post.voteCount = post.voteCount + 1
        } else {
            post.voteCount = post.voteCount - 1
        }
        voteRepository.save(
            Vote().apply {
                this.voteType = voteDto.voteType
                this.post = post
                this.user = authService.getCurrentUser()
            }
        )
        postRepository.save(post)
    }

}
