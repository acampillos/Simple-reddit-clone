package com.practice.redditclone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class RedditCloneApplication

fun main(args: Array<String>) {
	runApplication<RedditCloneApplication>(*args)
}
