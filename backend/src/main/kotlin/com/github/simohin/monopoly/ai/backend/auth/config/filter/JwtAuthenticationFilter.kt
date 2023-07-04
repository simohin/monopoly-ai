package com.github.simohin.monopoly.ai.backend.auth.config.filter

import com.github.simohin.monopoly.ai.backend.auth.BEARER_HEADER_PREFIX
import com.github.simohin.monopoly.ai.backend.auth.service.JwtProvider
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = exchange.request.headers
            .getFirst(HttpHeaders.AUTHORIZATION)
            .takeIf { StringUtils.hasText(it) && (it?.startsWith(BEARER_HEADER_PREFIX) ?: false) }
            ?.substring(BEARER_HEADER_PREFIX.length)
            ?.let { if (jwtProvider.validate(it)) it else null } ?: return chain.filter(exchange)

        val auth = jwtProvider.parse(token)

        val context = ReactiveSecurityContextHolder.withAuthentication(auth)

        return chain.filter(exchange).contextWrite(context)
    }

}
