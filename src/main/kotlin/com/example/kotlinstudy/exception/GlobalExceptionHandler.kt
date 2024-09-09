package com.example.kotlinstudy.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @PackageName : com.example.kotlinstudy.exception
 * @FileName : GlobalExceptionHandler
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RestControllerAdvice
class GlobalExceptionHandler {

    val log = KotlinLogging.logger {}

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e:MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.error { "handleMethodArgumentNotValidException $e" }

        val of = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.bindingResult)

        return ResponseEntity(of, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e:BusinessException): ResponseEntity<ErrorResponse> {
        log.error { "handleEntityNotFoundException $e" }

        val of = ErrorResponse.of(e.errorCode)
        return ResponseEntity(of, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}