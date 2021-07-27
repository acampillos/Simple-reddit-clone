package com.practice.redditclone.mapper

import com.practice.redditclone.dto.SubredditDto
import com.practice.redditclone.model.Subreddit
import org.springframework.stereotype.Component

@Component
class SubredditMapper {

    fun toDto(subreddit: Subreddit): SubredditDto =
        SubredditDto(
            id = subreddit.id,
            name = subreddit.name,
            description = subreddit.description,
            numberOfPosts = subreddit.posts.size
        )

    fun toEntity(subredditDto: SubredditDto): Subreddit =
        Subreddit().apply {
            this.name = subredditDto.name
            this.description = subredditDto.description
        }

}