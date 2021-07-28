package com.practice.redditclone.service

import com.practice.redditclone.dto.PostRequest
import com.practice.redditclone.dto.PostResponse
import com.practice.redditclone.exceptions.PostNotFoundException
import com.practice.redditclone.exceptions.SubredditNotFoundException
import com.practice.redditclone.mapper.PostMapper
import com.practice.redditclone.model.Post
import com.practice.redditclone.model.Subreddit
import com.practice.redditclone.model.User
import com.practice.redditclone.repository.PostRepository
import com.practice.redditclone.repository.SubredditRepository
import com.practice.redditclone.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val subreddtRepository: SubredditRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val postMapper: PostMapper
) {

    @Transactional
    fun save(postRequest: PostRequest) {
        val subreddit: Subreddit = subreddtRepository.findByName(postRequest.subredditName)
        if (subreddit == null) {
            throw SubredditNotFoundException(postRequest.subredditName)
        } else {
            val currentUser: User = authService.getCurrentUser()
            postRepository.save(postMapper.toEntity(postRequest, subreddit, currentUser))
        }
    }

    @Transactional(readOnly = true)
    fun getPost(id: Long): PostResponse {
        val post: Post = postRepository.findById(id).get()
        if (post == null) {
            throw PostNotFoundException(id.toString())
        } else {
            return postMapper.toPostResponse(post)
        }
    }

    @Transactional(readOnly = true)
    fun getAllPosts(): List<PostResponse> =
        postRepository.findAll()
            .stream()
            .map(postMapper::toPostResponse)
            .toList()

    @Transactional(readOnly = true)
    fun getPostsBySubreddit(subredditId: Long): List<PostResponse> {
        val subreddit: Subreddit = subreddtRepository.findById(subredditId).get()
        if (subreddit == null) {
            throw SubredditNotFoundException(subredditId.toString())
        } else {
            val posts: List<Post> = postRepository.findAllBySubreddit(subreddit)
            return posts.stream().map(postMapper::toPostResponse).toList()
        }
    }

    @Transactional(readOnly = true)
    fun getPostsByUsername(username: String): List<PostResponse> {
        val user: User = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return postRepository.findByUser(user)
            .map(postMapper::toPostResponse)
            .toList()
    }

}
