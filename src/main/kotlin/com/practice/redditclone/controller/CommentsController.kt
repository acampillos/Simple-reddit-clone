package com.practice.redditclone.controller

import com.practice.redditclone.dto.CommentDto
import com.practice.redditclone.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentsController(
    private val commentService: CommentService
) {

    @PostMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createComment(@RequestBody commentsDto: CommentDto): ResponseEntity<Void> {
        commentService.save(commentsDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping(
        value = ["/by-post/{postId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAllCommentsForPost(@PathVariable postId: Long): ResponseEntity<List<CommentDto>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getAllCommentsForPost(postId))
    }

    @GetMapping(
        value = ["/by-user/{username}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAllCommentsForUser(@PathVariable username: String): ResponseEntity<List<CommentDto>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getAllCommentsForUser(username))
    }

}