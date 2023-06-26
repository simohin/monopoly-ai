package com.github.simohin.monopoly.ai.backend.ws.controller

import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
@RequestMapping("sse")
class EventsController {

    @GetMapping("ping")
    fun ping(): Flux<ServerSentEvent<String>> = Flux.interval(Duration.ofSeconds(1))
        .map {
            ServerSentEvent.builder<String>()
                .id(it.toString())
                .event("base")
                .data("ping $it")
                .build()
        }
}
