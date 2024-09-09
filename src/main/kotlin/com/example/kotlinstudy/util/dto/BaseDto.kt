package com.example.kotlinstudy.util.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

/**
 * @PackageName : com.example.kotlinstudy.util.dto
 * @FileName : BaseDto
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@JsonPropertyOrder("id", "createAt", "updateAt")
open class BaseDto(
        var id: Long = 0,
        var createdAt: LocalDateTime = LocalDateTime.now(),
        var updatedAt: LocalDateTime = LocalDateTime.now()
) {

}