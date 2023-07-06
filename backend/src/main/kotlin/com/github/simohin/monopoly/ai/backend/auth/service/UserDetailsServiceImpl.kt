package com.github.simohin.monopoly.ai.backend.auth.service

import com.github.simohin.monopoly.ai.backend.auth.dao.document.UserDetailsImpl
import com.github.simohin.monopoly.ai.backend.auth.dao.repository.UserDetailsRepository
import com.github.simohin.monopoly.ai.backend.auth.model.enum.UserRole
import com.github.simohin.monopoly.ai.backend.auth.model.enum.toAuthorities
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class UserDetailsServiceImpl(
    private val userDetailsRepository: UserDetailsRepository,
    private val passwordEncoder: PasswordEncoder
) : ReactiveUserDetailsService {

    fun getUserDetails() = ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .flatMap { findByUsername((it.principal as User).username) }
        .map { it as UserDetailsImpl }

    override fun findByUsername(username: String) = userDetailsRepository.findByUsername(username)
        .switchIfEmpty(Mono.error(NoSuchElementException("User $username not found")))

    @Transactional
    fun create(username: String, password: String) = userDetailsRepository.existsByUsername(username)
        .filter { !it }
        .map {
            UserDetailsImpl(
                username,
                passwordEncoder.encode(password),
                mutableSetOf(UserRole.ROLE_USER).toAuthorities()
            )
        }
        .flatMap { userDetailsRepository.save(it) }
        .switchIfEmpty(Mono.error(IllegalArgumentException("User $username already exists")))
}
