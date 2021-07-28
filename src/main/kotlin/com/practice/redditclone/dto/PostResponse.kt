package com.practice.redditclone.dto

data class PostResponse(
    val id: Long,
    val postName: String,
    val url: String,
    val description: String,
    val userName: String,
    val subredditName: String
)