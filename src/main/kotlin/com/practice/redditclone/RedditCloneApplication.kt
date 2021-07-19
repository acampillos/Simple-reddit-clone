package com.practice.redditclone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedditCloneApplication

fun main(args: Array<String>) {
	runApplication<RedditCloneApplication>(*args)
}
