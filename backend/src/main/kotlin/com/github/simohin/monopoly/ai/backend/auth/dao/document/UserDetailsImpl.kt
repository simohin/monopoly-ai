package com.github.simohin.monopoly.ai.backend.auth.dao.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Document
data class UserDetailsImpl(
    @Id
    private val username: String,
    private val password: String,
    private val authorities: MutableCollection<out GrantedAuthority> = mutableSetOf(),
    private val nonExpired: Boolean = true,
    private val nonLocked: Boolean = true,
    private val credentialsNonExpired: Boolean = true,
    private val enabled: Boolean = true,
    val id: UUID = UUID.randomUUID(),
) : UserDetails {
    override fun getAuthorities() = authorities

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = nonExpired

    override fun isAccountNonLocked() = nonLocked

    override fun isCredentialsNonExpired() = credentialsNonExpired

    override fun isEnabled() = enabled
}
