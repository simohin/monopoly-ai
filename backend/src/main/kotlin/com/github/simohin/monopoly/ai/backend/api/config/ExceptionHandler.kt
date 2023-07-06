package com.github.simohin.monopoly.ai.backend.api.config

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class ExceptionHandler(
    attrs: ErrorAttributes,
    ctx: ApplicationContext,
    props: WebProperties,
    configurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(attrs, props.resources, ctx) {

    init {
        this.setMessageReaders(configurer.readers)
        this.setMessageWriters(configurer.writers)
    }

    override fun getRoutingFunction(attrs: ErrorAttributes) = route(RequestPredicates.all(), ::renderErrorResponse)

    private fun renderErrorResponse(req: ServerRequest): Mono<ServerResponse> {
        val error = getError(req)
        val status = when (error) {
            is NoSuchElementException -> HttpStatus.NOT_FOUND
            is BadCredentialsException -> HttpStatus.UNAUTHORIZED
            is DuplicateKeyException, is IllegalArgumentException -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        val attrs = getErrorAttributes(req, ErrorAttributeOptions.defaults())
        attrs["status"] = status.value()
        attrs["message"] = error.localizedMessage

        return ServerResponse.status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(attrs))
    }
}
