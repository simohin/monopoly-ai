package com.github.simohin.monopoly.ai.backend.dao.document

import com.github.simohin.monopoly.ai.backend.model.enum.RoomStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Room(
    val owner: UUID,
    @Id
    val id: UUID = UUID.randomUUID(),
    val status: RoomStatus = RoomStatus.CREATED,
    val players: MutableCollection<UUID> = mutableSetOf(owner)
)
