package com.practice.redditclone.exceptions

import org.springframework.mail.MailException

class SpringRedditException(errorMessage: String, e: MailException) : RuntimeException(errorMessage) {

}
