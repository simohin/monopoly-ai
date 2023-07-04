package com.github.simohin.monopoly.ai.backend.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class LogProvider {
    val log: Logger = when (this::class.isCompanion) {
        true -> this::class.java.declaringClass
        else -> this::class.java
    }.let {
        LoggerFactory.getLogger(it)
    }
}
