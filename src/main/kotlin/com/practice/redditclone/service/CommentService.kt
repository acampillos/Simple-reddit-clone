package com.practice.redditclone.service

import com.practice.redditclone.dto.CommentDto
import com.practice.redditclone.exceptions.PostNotFoundException
import com.practice.redditclone.mapper.CommentMapper
import com.practice.redditclone.model.Comment
import com.practice.redditclone.model.NotificationEmail
import com.practice.redditclone.model.Post
import com.practice.redditclone.model.User
import com.practice.redditclone.repository.CommentRepository
import com.practice.redditclone.repository.PostRepository
import com.practice.redditclone.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val POST_URL: String = "",
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val commentMapper: CommentMapper,
    private val commentRepository: CommentRepository,
    private val mailContentBuilder: MailContentBuilder,
    private val mailService: MailService
) {

    fun save(commentsDto: CommentDto) {
        val post: Post = postRepository.findById(commentsDto.postId).get()
        if (post == null) {
            throw PostNotFoundException(commentsDto.id.toString())
        } else {
            val comment: Comment = commentMapper.toEntity(commentsDto, post, authService.getCurrentUser())
            commentRepository.save(comment)
            val message = mailContentBuilder.build(post.user.username + " posted a comment on your post." + POST_URL)
            sendCommentNotification(message, post.user)
        }
    }

    private fun sendCommentNotification(message: String, user: User) {
        mailService.sendMail(NotificationEmail(user.username + " commented on your post", user.email, message))
    }

    fun getAllCommentsForPost(postId: Long): List<CommentDto> {
        val post: Post = postRepository.findById(postId).get()
        if (post == null) {
            throw PostNotFoundException(postId.toString())
        } else {
            return commentRepository.findByPost(post).map(commentMapper::toDto).toList()
        }
    }

    fun getAllCommentsForUser(userName: String): List<CommentDto> {
        val user: User = userRepository
            .findByUsername(userName) ?: throw UsernameNotFoundException(userName)
        return commentRepository.findAllByUser(user)
            .map(commentMapper::toDto)
            .toList()
    }


}