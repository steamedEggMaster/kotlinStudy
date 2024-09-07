package com.example.kotlinstudy.service

import com.example.kotlinstudy.domain.member.*
import com.example.kotlinstudy.domain.post.*
import com.example.kotlinstudy.service.common.FileUploaderService
import com.example.kotlinstudy.service.common.LocalFilterUploaderServiceImpl
import com.example.kotlinstudy.util.dto.SearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

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
        private val postRepository: PostRepository,
        private val localFileUploaderServiceImpl: FileUploaderService
) {

    //@PreAuthorize("hasAuthority('SUPER')") // SecurityCnfig의 @EnableMethodSecurity와 함께 사용되는 어노테이션
    // 메서드를 제한함!
    @Transactional(readOnly = true)
    fun findPosts(pageable: Pageable, searchCondition: SearchCondition): Page<PostRes> {

        return postRepository.findPosts(pageable, searchCondition).map { it.toDto() }

    }

    @Transactional
    fun savePost(dto: PostSaveReq): PostRes {
        return postRepository.save(dto.toEntity()).toDto()
    }

    @Transactional
    fun deletePost(id : Long){
        return postRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun findPostById(id:Long): PostRes {
        return postRepository.findById(id).orElseThrow().toDto()
    }

    fun savePostImg(image: MultipartFile): String {
        return localFileUploaderServiceImpl.upload(image)
    }

}