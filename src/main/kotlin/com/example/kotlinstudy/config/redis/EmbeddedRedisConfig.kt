package com.example.kotlinstudy.config.redis

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import redis.embedded.RedisServer
import kotlin.jvm.internal.Intrinsics.Kotlin

/**
 * @PackageName : com.example.kotlinstudy.config.redis
 * @FileName : LocalRedisConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class EmbeddedRedisConfig(

) {

    private val log = KotlinLogging.logger {  }

    lateinit var redisServer: RedisServer
    val port: Int = 6379

    @PostConstruct
    fun init(){

        log.info { "embedded redis Start port : $port" }

        this.redisServer = RedisServer(this.port)
        redisServer.start()
    }

    @PreDestroy
    fun destroy(){
        log.info { "embedded redis Stop!!" }

        this.redisServer.stop()
    }

}