package com.github.simohin.monopoly.ai.backend.api.dto

import java.util.*
import com.github.simohin.monopoly.ai.backend.dao.document.Player as PlayerDocument

data class Player(
    val id: UUID,
    val name: String
) {
    constructor(document: PlayerDocument) : this(document.id, document.name)
}
