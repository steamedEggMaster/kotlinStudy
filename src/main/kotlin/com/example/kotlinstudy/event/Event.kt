package com.example.kotlinstudy.event

import java.time.LocalDateTime

/**
 * @PackageName : com.example.kotlinstudy.event
 * @FileName : Event
 * @Author : noglass_gongdae
 * @Date : 2024-09-09
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
abstract class Event(
        open val timeStamp: LocalDateTime
) {

}