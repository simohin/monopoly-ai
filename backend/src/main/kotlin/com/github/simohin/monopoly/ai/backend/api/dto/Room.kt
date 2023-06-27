package com.github.simohin.monopoly.ai.backend.api.dto

import com.github.simohin.monopoly.ai.backend.model.enum.RoomStatus
import java.util.*
import com.github.simohin.monopoly.ai.backend.dao.document.Room as RoomDocument

data class Room(
    val id: UUID,
    val status: RoomStatus,
    val players: Collection<UUID>
) {
    constructor(document: RoomDocument) : this(document.id, document.status, document.players.toSet())
}
