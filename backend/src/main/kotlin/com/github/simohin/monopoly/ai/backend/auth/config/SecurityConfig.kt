package com.github.simohin.monopoly.ai.backend.auth.config

import com.github.simohin.monopoly.ai.backend.auth.config.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun chain(http: ServerHttpSecurity, filter: JwtAuthenticationFilter): SecurityWebFilterChain = http
        .csrf { it.disable() }
        .httpBasic { it.disable() }
        .formLogin { it.disable() }
        .logout { it.disable() }
        .authorizeExchange {
            it.pathMatchers("/auth/**").permitAll()
            it.anyExchange().authenticated()
        }
        .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build()

    @Bean
    fun authManager(userDetailsService: ReactiveUserDetailsService, passwordEncoder: PasswordEncoder) =
        UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
            .apply { setPasswordEncoder(passwordEncoder) }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
