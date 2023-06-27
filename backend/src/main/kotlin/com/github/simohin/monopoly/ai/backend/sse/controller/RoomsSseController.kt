package com.github.simohin.monopoly.ai.backend.sse.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.simohin.monopoly.ai.backend.service.RoomService
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("sse/rooms")
class RoomsSseController(
    private val roomService: RoomService,
    private val mapper: ObjectMapper
) {

    @GetMapping("{room-id}/join")
    fun playerJoinRoom(@PathVariable("room-id") roomId: UUID) =
        roomService.roomJoinSink.asFlux()
            .filter {
                it.roomId == roomId
            }.map {
                mapper.writeValueAsString(it)
            }.map {
                ServerSentEvent.builder<String>()
                    .id(it.toString())
                    .event("rooms")
                    .data(it)
                    .build()
            }
}
