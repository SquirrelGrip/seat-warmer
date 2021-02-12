package com.github.squirrelgrip.seat.controller

import com.github.squirrelgrip.seat.exception.FullyBookedException
import com.github.squirrelgrip.seat.exception.UnknownReservationException
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.RuntimeException
import org.springframework.web.context.request.WebRequest
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [FullyBookedException::class, UnknownReservationException::class])
    protected fun handleConflict(
        exception: RuntimeException, request: WebRequest
    ): ResponseEntity<Any> {
        val bodyOfResponse = exception.message
        return handleExceptionInternal(
            exception,
            bodyOfResponse,
            HttpHeaders(),
            HttpStatus.CONFLICT,
            request
        )
    }
}