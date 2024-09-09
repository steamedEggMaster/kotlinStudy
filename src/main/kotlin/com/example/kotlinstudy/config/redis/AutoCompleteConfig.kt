package com.example.kotlinstudy.config.redis

import net.okihouse.autocomplete.implement.AutocompleteServiceImpl
import net.okihouse.autocomplete.key.AutocompleteKeyServiceImpl
import net.okihouse.autocomplete.repository.AutocompleteKeyRepository
import net.okihouse.autocomplete.repository.AutocompleteRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.StringRedisTemplate

/**
 * @PackageName : com.example.kotlinstudy.config.redis
 * @FileName : AutoCompleteConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-08
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class AutoCompleteConfig( // Redis를 활용한 검색어 자동완성
        private val stringRedisTemplate: StringRedisTemplate
) {

    @Bean(name = ["autocompleteKeyRepository", "keyRepository"])
    fun keyRepository() : AutocompleteKeyRepository? {
        return AutocompleteKeyServiceImpl(stringRedisTemplate)
    }

    @Bean(name = ["autocompleteRepository"])
    fun autocompleteRepository(autocompleteRepository: AutocompleteKeyRepository) : AutocompleteRepository {
        return AutocompleteServiceImpl(stringRedisTemplate, autocompleteRepository)
    }
}