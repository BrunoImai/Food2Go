package com.food2you.foodserver.exceptions

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(BAD_REQUEST)
class BadRequestException(
    message: String = BAD_REQUEST.reasonPhrase,
    cause: Throwable? = null
): IllegalArgumentException(message, cause)

@ResponseStatus(NOT_FOUND)
class EntityNotFoundException(
    message: String = BAD_REQUEST.reasonPhrase,
    cause: Throwable? = null
): IllegalArgumentException(message, cause)