package com.practice.redditclone.dto

import com.practice.redditclone.model.VoteType

data class VoteDto(
    val voteType: VoteType,
    val postId: Long
)