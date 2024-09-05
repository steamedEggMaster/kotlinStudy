package com.example.kotlinstudy.config.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * @PackageName : com.example.kotlinstudy.config.redis
 * @FileName : RedisConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class RedisConfig {

    // 이 두개의 Bean은 짝궁이다-----------------------------------------------
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericToStringSerializer(Any::class.java)

        // : redis는 데이터를 저장시, 기본적으로 String 기반 직렬화 방식 사용
        //   -> index.toString() 만 사용 시, StringRedisSerializer 가 아닌 자바 직렬화 방식이 사용됨
        //   -> 성능도 떨어지고, 바이너리 형식으로 변환되어 알아볼 수 없는 문자가 됨
        //   -> 즉, StringRedisSerializer 이걸 해주자!

        return redisTemplate
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration("localhost", 6379)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }
    // -----------------------------------------------

}