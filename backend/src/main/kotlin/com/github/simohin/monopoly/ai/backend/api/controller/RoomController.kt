package com.github.simohin.monopoly.ai.backend.api.controller

import com.github.simohin.monopoly.ai.backend.api.dto.AddPlayerRequest
import com.github.simohin.monopoly.ai.backend.api.dto.Room
import com.github.simohin.monopoly.ai.backend.service.RoomService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("rooms")
class RoomController(
    private val roomService: RoomService
) {

    @PostMapping
    fun create() = roomService.create()
        .map { Room(it) }

    @GetMapping("{id}")
    fun get(@PathVariable id: UUID) = roomService.get(id).map { Room(it) }

    @GetMapping
    fun get() = roomService.get().map { Room(it) }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID) =
        roomService.delete(id)
            .then(Mono.just(ServerResponse.status(HttpStatus.NO_CONTENT).build()))

    @PostMapping("{id}/players")
    fun addPlayer(@PathVariable id: UUID, @RequestBody dto: AddPlayerRequest) = roomService.join(id, dto.id)
        .map { Room(it) }

}
