package com.practice.redditclone.service

//import org.springframework.security.core.userdetails.User
//import com.practice.redditclone.repository.UserRepository
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//class UserDetailsServiceImpl(
//    private val userRepository: UserRepository
//) : UserDetailsService {
//
//    @Transactional(readOnly = true)
//    override fun loadUserByUsername(username: String): UserDetails {
//        val user: com.practice.redditclone.model.User = userRepository
//            .findByUsername(username) ?: throw UsernameNotFoundException("No user found with username: $username")
//        return User(
//            user.username,
//            user.password,
//            user.enabled,
//            true,
//            true,
//            true,
//            getAuthorities("USER")
//        )
//    }
//
//    private fun getAuthorities(role: String): Collection<GrantedAuthority> =
//        listOf(SimpleGrantedAuthority(role))
//}

import com.practice.redditclone.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
): UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val user= userRepository.findByUsername(username)
        checkNotNull(user) { "Пользователь $username не найден" }
        return User(user.username,
            user.password,
            user.enabled,
            true,
            true,
            true,
            getAuthorities("USER")
        )
    }

    private fun getAuthorities(role: String): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }
}