package com.example.kotlinstudy.config.cache

import com.github.benmanes.caffeine.cache.Caffeine
import mu.KotlinLogging
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @PackageName : com.example.kotlinstudy.config.cache
 * @FileName : CacheConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-08
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
@EnableCaching // 캐시를 사용할게
class CacheConfig {

    private val log = KotlinLogging.logger {  }

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = SimpleCacheManager()

        val caches: List<CaffeineCache> = Arrays.stream(CacheType.entries.toTypedArray())
                .map { cache -> makeCaffeineCache(cache) }.toList()

        cacheManager.setCaches(caches)

        return cacheManager
    }

    // localCache
    private fun makeCaffeineCache(cache: CacheType): CaffeineCache {
        val caffeineCache = CaffeineCache(cache.cacheName,
                Caffeine.newBuilder().recordStats()
                        .expireAfterWrite(cache.expiredAfterWrite, TimeUnit.SECONDS)
                        .maximumSize(cache.maximumSize)
                        .removalListener { key: Any?, value: Any?, cause ->
                            log.info { "key $key was evicted $cause: $value" }
                        }
                        .build()
        )
        return caffeineCache
    }

}