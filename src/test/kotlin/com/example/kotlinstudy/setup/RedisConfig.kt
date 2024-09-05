package com.example.kotlinstudy.setup

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * @PackageName : com.example.kotlinstudy.setup
 * @FileName : RedisConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@TestConfiguration
class RedisConfig {
    // 이 두개의 Bean은 짝궁이다-----------------------------------------------
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory

        // 키는 String으로 직렬화
        redisTemplate.keySerializer = StringRedisSerializer()

        // 값을 String으로 직렬화 (또는 필요한 경우에 맞는 직렬화기 설정)
        redisTemplate.valueSerializer = GenericToStringSerializer(Any::class.java)

        return redisTemplate
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration("localhost", 6379)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }
    // -----------------------------------------------

}