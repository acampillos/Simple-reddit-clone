package com.practice.redditclone.service

import com.practice.redditclone.dto.SubredditDto
import com.practice.redditclone.model.Subreddit
import com.practice.redditclone.repository.SubredditRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubredditService(
    private val subredditRepository: SubredditRepository
) {

    @Transactional
    fun save(subredditDto: SubredditDto): SubredditDto {
        val save: Subreddit = subredditRepository.save(mapSubredditDto(subredditDto))
        subredditDto.id = save.id
        return subredditDto
    }

    @Transactional(readOnly = true)
    fun getAll(): List<SubredditDto> =
        subredditRepository.findAll()
            .stream()
            .map(this::mapToDto)
            .toList()

    private fun mapSubredditDto(subredditDto: SubredditDto): Subreddit =
        Subreddit().apply {
            this.name = subredditDto.name
            this.description = subredditDto.description
        }

    private fun mapToDto(subreddit: Subreddit): SubredditDto =
        SubredditDto(
            id = subreddit.id,
            name = subreddit.name,
            description = subreddit.description,
            numberOfPosts = subreddit.posts.size
        )

}