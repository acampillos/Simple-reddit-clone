package com.practice.redditclone

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedditCloneApplicationTests {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    var port: Int = 0

    @Test
    fun contextLoads() {
    }

}
