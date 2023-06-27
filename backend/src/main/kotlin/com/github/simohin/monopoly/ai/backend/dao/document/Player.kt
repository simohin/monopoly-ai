package com.github.simohin.monopoly.ai.backend.dao.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Player(
    val name: String,
    @Id
    val id: UUID = UUID.randomUUID()
)
