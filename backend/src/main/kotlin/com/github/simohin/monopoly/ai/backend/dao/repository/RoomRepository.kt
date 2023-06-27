package com.github.simohin.monopoly.ai.backend.dao.repository

import com.github.simohin.monopoly.ai.backend.dao.document.Room
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomRepository : ReactiveMongoRepository<Room, UUID>
