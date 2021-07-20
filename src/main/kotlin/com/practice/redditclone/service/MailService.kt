package com.practice.redditclone.service

import com.practice.redditclone.exceptions.SpringRedditException
import mu.KotlinLogging
import com.practice.redditclone.model.NotificationEmail
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage

@Service
class MailService (
    private val mailSender: JavaMailSender,
    private val mailContentBuilder: MailContentBuilder){

    companion object {
        private val log = KotlinLogging.logger {}
    }


    @Async
    fun sendMail(notificationEmail : NotificationEmail){
        val messagePreparator = MimeMessagePreparator { mimeMessage: MimeMessage? ->
            val messageHelper = MimeMessageHelper(mimeMessage!!)
            messageHelper.setFrom("springreddit@email.com")
            messageHelper.setTo(notificationEmail.recipient)
            messageHelper.setSubject(notificationEmail.subject)
            messageHelper.setText(notificationEmail.body)
        }
        try {
            mailSender.send(messagePreparator)
            log.info { "Activation email sent." }
        } catch (e : MailException){
            log.error { "Exception occurred when sending mail" + e.message }
            throw SpringRedditException("Exception occurred when sending mail to " + notificationEmail.recipient, e)
        }
    }

}