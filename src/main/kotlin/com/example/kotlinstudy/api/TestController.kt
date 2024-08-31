package com.example.kotlinstudy.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @PackageName : com.example.kotlinstudy.api
 * @FileName : TestController
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RestController
class TestController(

) {

    @GetMapping("/health")
    fun healthTest():String {
        return "hello kotlin-blog"
    } // 아래 코드도 가능!
//    @GetMapping("/health")
//    fun healthTest():String = "hello kotlin-blog"

}