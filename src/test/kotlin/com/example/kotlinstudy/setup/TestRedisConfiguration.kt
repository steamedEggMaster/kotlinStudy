package com.example.kotlinstudy.setup

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import redis.embedded.RedisServer

/**
 * @PackageName : com.example.kotlinstudy.setup
 * @FileName : TestRedisConfiguration
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@TestConfiguration
class TestRedisConfiguration {

    private val redisServer: RedisServer = RedisServer(6379)

    @PostConstruct
    fun init(){
        redisServer.start()
    }

    @PreDestroy
    fun destroy(){
        redisServer.stop()
    }

}