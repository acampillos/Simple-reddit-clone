package com.practice.redditclone.service

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailContentBuilder(
    private val templateEngine: TemplateEngine
) {

    fun build(message: String) : String {
        Context().apply {
            setVariable("message", message)
        }.let {
            return templateEngine.process("mailTemplate", it)
        }
    }

}