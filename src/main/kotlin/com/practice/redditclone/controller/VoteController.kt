package com.practice.redditclone.controller

import com.practice.redditclone.dto.VoteDto
import com.practice.redditclone.service.VoteService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/votes")
class VoteController(
    private val voteService: VoteService
) {

    @PostMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun vote(@RequestBody voteDto: VoteDto): ResponseEntity<Void> {
        voteService.vote(voteDto)
        return ResponseEntity(HttpStatus.OK)
    }

}