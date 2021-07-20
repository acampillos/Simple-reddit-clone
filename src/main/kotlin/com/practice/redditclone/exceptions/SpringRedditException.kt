package com.practice.redditclone.exceptions

import org.springframework.mail.MailException

class SpringRedditException(errorMessage: String) : RuntimeException(errorMessage) {

    constructor(errorMessage: String, e: MailException) : this(errorMessage) {

    }


}
