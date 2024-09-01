package com.example.kotlinstudy.exception

import com.fasterxml.jackson.annotation.JsonFormat

/**
 * @PackageName : com.example.kotlinstudy.exception
 * @FileName : ErrorCode
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(
        val code:String,
        val message:String
) {
    INVALID_INPUT_VALUE("C001", "invalid input value"),
    ENTITY_NOT_FOUND("C002", "Entity not found")
}