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
    private val playerRepository: PlayerRepository
) {

    fun create(name: String) = playerRepository.existsByName(name)
        .filter { !it }
        .flatMap { playerRepository.save(Player(name)) }
        .switchIfEmpty { Mono.error(DuplicateKeyException("User with name $name already exists")) }

    fun delete(id: UUID) = playerRepository.deleteById(id)

    fun get(id: UUID) = playerRepository.findById(id)
        .switchIfEmpty { Mono.error(NoSuchElementException("Player with id $id not found")) }

    fun get() = playerRepository.findAll()

}
