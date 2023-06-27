package com.github.simohin.monopoly.ai.backend.api.controller

import com.github.simohin.monopoly.ai.backend.api.dto.CreatePlayerRequest
import com.github.simohin.monopoly.ai.backend.api.dto.Player
import com.github.simohin.monopoly.ai.backend.service.PlayerService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("players")
class PlayersController(
    private val playerService: PlayerService
) {

    @PostMapping
    fun create(@RequestBody dto: CreatePlayerRequest) = playerService.create(dto.name)
        .map {
            ServerResponse.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, it.id.toString())
                .bodyValue(Player(it))
        }

    @GetMapping("{id}")
    fun get(@PathVariable id: UUID) = playerService.get(id).map { Player(it) }

    @GetMapping
    fun get() = playerService.get().map { Player(it) }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID) =
        playerService.delete(id)
            .then(Mono.just(ServerResponse.status(HttpStatus.NO_CONTENT).build()))

}
