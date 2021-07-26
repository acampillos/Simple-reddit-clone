package com.practice.redditclone.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class SecurityConfig(
    @Qualifier("userDetailsServiceImpl") private val userDetailsService: UserDetailsService,
) : WebSecurityConfigurerAdapter() {

    override fun configure(httpSecurity: HttpSecurity) {
        // Disabling CSRF attacks protection as REST APIs are stateless by definition and
        // we are using JSON web tokens for auth

        // We allow all incoming requests to out backend API with endpoint URL starts with /api/auth/
        // Any other that doesnt match the pattern needs to be authenticated
        httpSecurity.cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated()
    }

    @Autowired
    fun configureGlobal(authenticationManagerBuilder: AuthenticationManagerBuilder){
        authenticationManagerBuilder.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean // because PasswordEncoder is an interface
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

}