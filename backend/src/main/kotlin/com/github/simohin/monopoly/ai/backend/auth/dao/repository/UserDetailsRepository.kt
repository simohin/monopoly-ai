package com.github.simohin.monopoly.ai.backend.auth.dao.repository

import com.github.simohin.monopoly.ai.backend.auth.dao.document.UserDetailsImpl
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface UserDetailsRepository : ReactiveMongoRepository<UserDetailsImpl, UUID> {

    fun findByUsername(username: String): Mono<UserDetails>
    fun existsByUsername(username: String): Mono<Boolean>
}
