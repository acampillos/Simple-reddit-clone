package com.practice.redditclone.controller

import com.practice.redditclone.dto.SubredditDto
import com.practice.redditclone.service.SubredditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subreddit")
class SubredditController(
    private val subredditService: SubredditService
) {

    @PostMapping
    fun createSubreddit(@RequestBody subredditDto: SubredditDto): ResponseEntity<SubredditDto> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(subredditService.save(subredditDto))

    @GetMapping
    fun getAllSubreddits(): ResponseEntity<List<SubredditDto>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(subredditService.getAll())

    @GetMapping("/{id}")
    fun getSubreddit(@PathVariable id: Long): ResponseEntity<SubredditDto> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(subredditService.getSubreddit(id))

}