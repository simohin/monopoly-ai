package com.github.simohin.monopoly.ai.backend.service

import com.github.simohin.monopoly.ai.backend.dao.document.Player
import com.github.simohin.monopoly.ai.backend.dao.repository.PlayerRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.util.*

@Service
class PlayerService(
    private val repository: PlayerRepository
) {

    fun create(name: String) = repository.existsByName(name)
        .filter { !it }
        .flatMap { repository.save(Player(name)) }
        .switchIfEmpty { Mono.error(DuplicateKeyException("User with name $name already exists")) }

    fun delete(id: UUID) = repository.deleteById(id)

    fun get(id: UUID) = repository.findById(id)
        .switchIfEmpty { Mono.error(NoSuchElementException("Player with id $id not found")) }

    fun get() = repository.findAll()

}
