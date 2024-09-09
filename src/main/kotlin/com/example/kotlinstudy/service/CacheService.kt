package com.example.kotlinstudy.service

import com.example.kotlinstudy.config.cache.CacheType
import com.example.kotlinstudy.domain.post.PostRepository
import mu.KotlinLogging
import net.okihouse.autocomplete.repository.AutocompleteRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : CacheService
 * @Author : noglass_gongdae
 * @Date : 2024-09-08
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class CacheService(
        private val autocompleteRepository: AutocompleteRepository,
        private val postRepository: PostRepository
) {
    private val log = KotlinLogging.logger {  }

    //@Cacheable(cacheNames = [ CacheType.postName ], key = "'fixedKey'")
    @Cacheable(cacheNames = [ CacheType.Constant.postName ], key = "T(com.example.kotlinstudy.config.cache.CacheType).POST_NAME.cacheKey")
    // 해당 DB 접근 부분만 잘 캐싱되는 것을 확인 가능
    fun addAutoCompletePostTitle(): AutocompleteRepository {
        postRepository.findAll().forEach {
            autocompleteRepository.add(it.title)
        }
        return autocompleteRepository
    }
}