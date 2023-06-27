package com.github.simohin.monopoly.ai.backend.dao.repository

import com.github.simohin.monopoly.ai.backend.dao.document.Player
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface PlayerRepository : ReactiveMongoRepository<Player, UUID> {
    fun findByName(name: String): Mono<Player>
    fun existsByName(name: String): Mono<Boolean>
}
