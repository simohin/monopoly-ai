package com.github.simohin.monopoly.ai.backend.sse.controller

import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*

@RestController
@RequestMapping("sse/ping")
class PingSseController {

    @GetMapping
    fun ping(): Flux<ServerSentEvent<String>> = Flux.interval(Duration.ofSeconds(1))
        .map {
            ServerSentEvent.builder<String>()
                .id(it.toString())
                .event("base")
                .data("ping $it")
                .build()
        }

}
