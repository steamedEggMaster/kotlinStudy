package com.example.kotlinstudy.domain

import com.example.kotlinstudy.config.security.JwtManager
import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration
import java.util.*

/**
 * @PackageName : com.example.kotlinstudy.domain
 * @FileName : RedisRepositoryImpl
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */


class RedisRepositoryImpl(
        private val redisTemplate: RedisTemplate<String, Any>
) : InMemoryRepository {

    private val log = KotlinLogging.logger {  }

    init {

    }

    override fun clear() {
        val keys = redisTemplate.keys("*")
        for(key in keys){
            redisTemplate.delete(key)
        }
    }

    override fun remove(key: String): Any {
        return redisTemplate.delete(key)
    }

    override fun findAll(): MutableList<Any> {
        val keys = redisTemplate.keys("*")
        val list = mutableListOf<Any>()

        for (key in keys) {
            val map = mutableMapOf<String, Any>()
            val value = findByKey(key)
            map[key] = value

            list.add(map)
        }
        return list
    }

    override fun findByKey(key: String): Any {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key)).orElseThrow()
    }


    override fun save(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value, Duration.ofDays(JwtManager.getRefreshTokenDay()))
    }
}