package com.practice.redditclone.dto

data class PostRequest(
    val postId: Long,
    val subredditName: String,
    val postName: String,
    val url: String,
    val description: String
)
