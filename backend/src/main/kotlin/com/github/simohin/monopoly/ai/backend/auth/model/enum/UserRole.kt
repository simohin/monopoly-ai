package com.github.simohin.monopoly.ai.backend.auth.model.enum

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class UserRole {
    ROLE_USER,
    ROLE_ADMIN
}

fun Collection<UserRole>.toAuthorities() = map { SimpleGrantedAuthority(it.name) }.toMutableSet()
fun Collection<GrantedAuthority>.toRoles() = map { UserRole.valueOf(it.authority) }.toMutableSet()
