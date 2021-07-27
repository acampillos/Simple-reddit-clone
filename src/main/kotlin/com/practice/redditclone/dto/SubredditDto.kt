package com.practice.redditclone.dto

data class SubredditDto(
    var id: Long?,
    val name: String,
    val description: String,
    val numberOfPosts: Int = 0
)
