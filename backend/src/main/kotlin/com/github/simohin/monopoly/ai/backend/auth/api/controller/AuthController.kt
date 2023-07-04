package com.github.simohin.monopoly.ai.backend.auth.api.controller

import com.github.simohin.monopoly.ai.backend.auth.BEARER_HEADER_PREFIX
import com.github.simohin.monopoly.ai.backend.auth.api.dto.AuthRequest
import com.github.simohin.monopoly.ai.backend.auth.api.dto.AuthResponse
import com.github.simohin.monopoly.ai.backend.auth.service.JwtProvider
import com.github.simohin.monopoly.ai.backend.auth.service.UserDetailsServiceImpl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/auth")
class AuthController(
    private val jwtProvider: JwtProvider,
    private val authManager: ReactiveAuthenticationManager,
    private val userDetailsService: UserDetailsServiceImpl
) {

    @PostMapping("/tokens")
    fun login(@RequestBody authRequest: Mono<AuthRequest>) = authRequest
        .map { it.login to it.password }
        .authorize()

    @PostMapping("/users")
    fun register(@RequestBody authRequest: Mono<AuthRequest>) = authRequest.flatMap {
        userDetailsService.create(it.login, it.password)
    }.map { it.username to it.password }
        .authorize()

    private fun Mono<Pair<String, String>>.authorize() = flatMap {
        val username = it.first
        val password = it.second
        authManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            .map { auth -> jwtProvider.create(auth) }
    }
        .map {
            val headers = HttpHeaders()
            headers[HttpHeaders.AUTHORIZATION] = BEARER_HEADER_PREFIX + it
            ResponseEntity(AuthResponse(it), headers, HttpStatus.OK)
        }

}
