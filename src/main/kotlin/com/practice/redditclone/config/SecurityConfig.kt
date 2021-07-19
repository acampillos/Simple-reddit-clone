package com.practice.redditclone.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        // Disabling CSRF attacks protection as REST APIs are stateless by definition and
        // we are using JSON web tokens for auth

        // We allow all incoming requests to out backend API with endpoint URL starts with /api/auth/
        // Any other that doesnt match the pattern needs to be authenticated
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated()
    }

    @Bean // because PasswordEncoder is an interface
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()
}