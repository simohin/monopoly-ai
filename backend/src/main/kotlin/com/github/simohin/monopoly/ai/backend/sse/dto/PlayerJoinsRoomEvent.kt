package com.github.simohin.monopoly.ai.backend.sse.dto

import java.util.*

data class PlayerJoinsRoomEvent(
    val playerId: UUID,
    val roomId: UUID,
)
