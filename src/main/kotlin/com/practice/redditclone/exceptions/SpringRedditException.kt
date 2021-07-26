package com.practice.redditclone.exceptions

class SpringRedditException(errorMessage: String) : RuntimeException(errorMessage) {

    constructor(errorMessage: String, e: Exception) : this(errorMessage) {}

}
