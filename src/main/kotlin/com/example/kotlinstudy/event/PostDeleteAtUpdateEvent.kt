package com.example.kotlinstudy.event

import java.time.LocalDateTime

/**
 * @PackageName : com.example.kotlinstudy.event
 * @FileName : PostDeleteAtUpdateEvent
 * @Author : noglass_gongdae
 * @Date : 2024-09-09
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
data class PostDeleteAtUpdateEvent(
        override val timeStamp: LocalDateTime
) : Event(timeStamp)
