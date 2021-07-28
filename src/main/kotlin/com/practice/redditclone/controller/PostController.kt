package com.practice.redditclone.controller

import com.practice.redditclone.dto.PostRequest
import com.practice.redditclone.dto.PostResponse
import com.practice.redditclone.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(@RequestBody postRequest: PostRequest): ResponseEntity<Void> {
        postService.save(postRequest)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): ResponseEntity<PostResponse> =
        ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id))

    @GetMapping("/")
    fun getAllPosts(): ResponseEntity<List<PostResponse>> =
        ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts())

    @GetMapping("/by-subreddit/{id}")
    fun getPostsBySubreddit(id: Long): ResponseEntity<List<PostResponse>> =
        ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id))

    @GetMapping("/by-user/{name}")
    fun getPostsByUsername(username: String): ResponseEntity<List<PostResponse>> =
        ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username))

}