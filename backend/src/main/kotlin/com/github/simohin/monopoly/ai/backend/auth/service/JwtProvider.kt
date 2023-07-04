package com.github.simohin.monopoly.ai.backend.auth.service

import com.github.simohin.monopoly.ai.backend.auth.AUTHORITIES_DELIMITER
import com.github.simohin.monopoly.ai.backend.auth.AUTHORITIES_KEY
import com.github.simohin.monopoly.ai.backend.util.LogProvider
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.nio.charset.Charset
import java.time.Duration
import java.time.OffsetDateTime
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    @Value("\${jwt.secret:secret}")
    private val secret: String,
    @Value("\${jwt.ttl:PT1H}")
    private val ttl: Duration,
) {
    companion object : LogProvider()

    private val key = SecretKeySpec(secret.toByteArray(Charset.defaultCharset()), SignatureAlgorithm.HS256.jcaName)
    private val parser = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()

    fun create(auth: Authentication): String {
        val claims = Jwts.claims().setSubject(auth.name)
        claims[AUTHORITIES_KEY] = auth.authorities.joinToString(AUTHORITIES_DELIMITER) { it.authority }
        val now = OffsetDateTime.now()
        val validity = now.plus(ttl)
        val key = key
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(validity.toInstant()))
            .signWith(key)
            .compact()
    }

    fun parse(token: String): Authentication = parser.parseClaimsJws(token).body.let { claims ->
        val authorities =
            AuthorityUtils.commaSeparatedStringToAuthorityList(claims[AUTHORITIES_KEY].toString())
        val principal = User(claims.subject, "", authorities)
        UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validate(token: String) = try {
        StringUtils.hasText(token) && parser.parseClaimsJws(token).body.expiration.after(Date())
    } catch (e: Throwable) {
        when (e) {
            is JwtException, is IllegalArgumentException -> log.warn(e.localizedMessage)
            else -> throw e
        }
        false
    }
}
