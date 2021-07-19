package com.practice.redditclone.model

enum class VoteType(val direction: Int = 0) {
    UPVOTE(1),
    DOWNVOTE(-1);
}