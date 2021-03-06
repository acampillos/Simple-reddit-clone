package com.practice.redditclone.service

import com.practice.redditclone.dto.SubredditDto
import com.practice.redditclone.exceptions.SpringRedditException
import com.practice.redditclone.mapper.SubredditMapper
import com.practice.redditclone.model.Subreddit
import com.practice.redditclone.repository.SubredditRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SubredditService(
    private val subredditRepository: SubredditRepository,
    private val subredditMapper: SubredditMapper
) {

    @Transactional
    fun save(subredditDto: SubredditDto): SubredditDto {
        val save: Subreddit = subredditRepository.save(subredditMapper.toEntity(subredditDto))
        subredditDto.id = save.id
        return subredditDto
    }

    @Transactional(readOnly = true)
    fun getAll(): List<SubredditDto> =
        subredditRepository.findAll()
            .stream()
            .map(subredditMapper::toDto)
            .toList()

    @Transactional(readOnly = true)
    fun getSubreddit(id: Long): SubredditDto? {
        val subreddit = subredditRepository.findById(id)

        if(subreddit == null){
            throw SpringRedditException("No subreddit found with ID $id")
        } else {
            return subredditMapper.toDto(subreddit.get())
        }

    }

}