package com.example.kotlinstudy.service

import com.example.kotlinstudy.domain.post.Post
import com.example.kotlinstudy.domain.post.PostRepository
import com.example.kotlinstudy.domain.post.PostRes
import com.example.kotlinstudy.domain.post.toDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : Postservice
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class PostService(
        private val postRepository: PostRepository
) {

    @Transactional(readOnly = true)
    fun findPosts(pageable: Pageable): Page<PostRes> {

        return postRepository.findPosts(pageable).map { it.toDto() }

    }
}