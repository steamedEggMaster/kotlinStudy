package com.example.kotlinstudy.web

import com.example.kotlinstudy.service.PostService
import com.example.kotlinstudy.util.dto.SearchCondition
import com.example.kotlinstudy.util.dto.SearchType
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
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

        private val postService: PostService

) {
    private val log = KotlinLogging.logger {  }

    @GetMapping("/autocomplete")
    fun autoCompleteTest(@RequestParam word:String): MutableList<String> {
        return postService.autoCompletePostTitle(word)
    }

    @GetMapping("/health")
    fun healthTest():String {
        return "hello kotlin-blog"
    } // 아래 코드도 가능!
//    @GetMapping("/health")
//    fun healthTest():String = "hello kotlin-blog"

    @GetMapping("/error") // security Default redirect URL
    fun errorTest():String{
        return "error"
    }

    @GetMapping("/enum")
    fun enumTest(searchCondition: SearchCondition): String {
        log.info { """
            $searchCondition
            
            ${searchCondition.searchType}
            ${searchCondition.keyword}
        """.trimIndent() }
        return "test"
    }

    @GetMapping("/enum2")
    fun enumTest2(searchType: SearchType): String {

        return searchType.name
    }
}