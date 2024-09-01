package com.example.kotlinstudy.api

import com.example.kotlinstudy.domain.post.PostSaveReq
import com.example.kotlinstudy.service.PostService
import com.example.kotlinstudy.util.value.CmResDto
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * @PackageName : com.example.kotlinstudy.api
 * @FileName : PostController
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RequestMapping("/v1")
@RestController
class PostController(
        private val postService: PostService
) {

    @GetMapping("/posts")
    fun findPosts(@PageableDefault(size = 10) pageable: Pageable) : CmResDto<*> {
        return CmResDto(HttpStatus.OK, "find posts", postService.findPosts(pageable));
    }

    @GetMapping("/post/{id}")
    fun findById(@PathVariable id:Long): CmResDto<Any> {
        return CmResDto(HttpStatus.OK, "find Member by id", postService.findPostById(id))
    }

    @DeleteMapping("/post/{id}")
    fun deleteById(@PathVariable id:Long): CmResDto<Any> {
        return CmResDto(HttpStatus.OK, "delete Member by id", postService.deletePost(id))
    }

    @PostMapping("/post")
    fun save(@Valid @RequestBody dto: PostSaveReq): CmResDto<*> {
        return CmResDto(HttpStatus.OK, "save Member", postService.savePost(dto))

    }

}