package com.example.kotlinstudy.setup

import com.example.kotlinstudy.domain.RedisRepositoryImpl
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate

/**
 * @PackageName : com.example.kotlinstudy.setup
 * @FileName : SecurityConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Import(value = [ RedisConfig::class ])
@TestConfiguration
class SecurityConfig(
        private val redisTemplate: RedisTemplate<String, Any>
) {

    @Bean
    fun inMemoryRepository(): RedisRepositoryImpl {
        return RedisRepositoryImpl(redisTemplate)
    }
}