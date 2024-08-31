package com.example.kotlinstudy.api

import com.example.kotlinstudy.service.PostService
import com.example.kotlinstudy.util.value.CmResDto
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @PackageName : com.example.kotlinstudy.api
 * @FileName : PostController
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RestController
class PostController(
        private val postService: PostService
) {

    @GetMapping("/posts")
    fun findPosts(@PageableDefault(size = 10) pageable: Pageable) : CmResDto<*> {
        return CmResDto(HttpStatus.OK, "find posts", postService.findPosts(pageable));
    }

}