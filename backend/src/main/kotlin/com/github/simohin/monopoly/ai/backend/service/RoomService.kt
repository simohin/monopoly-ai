package com.github.simohin.monopoly.ai.backend.service

import com.github.simohin.monopoly.ai.backend.auth.service.UserDetailsServiceImpl
import com.github.simohin.monopoly.ai.backend.dao.document.Room
import com.github.simohin.monopoly.ai.backend.dao.repository.RoomRepository
import com.github.simohin.monopoly.ai.backend.sse.dto.PlayerJoinsRoomEvent
import com.github.simohin.monopoly.ai.backend.util.LogProvider
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.EmitResult
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toFlux
import java.util.*

@Service
class RoomService(
    private val repository: RoomRepository,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) {

    companion object : LogProvider()

    val roomJoinSink = Sinks.many().multicast().onBackpressureBuffer<PlayerJoinsRoomEvent>()

    fun create() =
        userDetailsServiceImpl.getUserDetails()
            .toFlux()
            .flatMap { repository.save(Room(it.id)) }


    fun delete(id: UUID) = repository.deleteById(id)

    fun get(id: UUID) = repository.findById(id)
        .switchIfEmpty { Mono.error(NoSuchElementException("Room with id $id not found")) }

    fun get() = userDetailsServiceImpl.getUserDetails()
        .toFlux()
        .flatMap { userDetails ->
            repository.findAll()
                .filter { it.players.contains(userDetails.id) }
        }

    fun join(roomId: UUID) = userDetailsServiceImpl.getUserDetails()
        .toFlux()
        .flatMap {
            get(roomId)
                .doOnNext { room ->
                    roomJoinSink.emitNext(PlayerJoinsRoomEvent(it.id, room.id), handleEmitError)
                }.flatMap { room ->
                    room.players.add(it.id)
                    repository.save(room)
                }
        }

    private val handleEmitError = { st: SignalType, res: EmitResult ->
        log.error("Failed to emit event with signal type $st and emit result $res")
        true
    }

}
