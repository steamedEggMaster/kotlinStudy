package com.example.kotlinstudy.domain

import com.example.kotlinstudy.config.security.JwtManager
import mu.KotlinLogging
import net.jodah.expiringmap.ExpirationPolicy
import net.jodah.expiringmap.ExpiringMap
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * @PackageName : com.example.kotlinstudy.domain
 * @FileName : HashMapRepositoryImpl
 * @Author : noglass_gongdae
 * @Date : 2024-09-04
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

//@Repository
open class HashMapRepositoryImpl(

) : InMemoryRepository {
    private val log = KotlinLogging.logger {  }
    //private val store = ConcurrentHashMap<String, Any>()
    //ConcurrentHashMap을 사용한 InMemory는 잘 사용 X
    // why? 유효기간 등을 설정할 수가 없음
    private val store:MutableMap<String, Any> = ExpiringMap.builder()
            .expiration(JwtManager.getRefreshTokenDay(), TimeUnit.DAYS)
            .expirationPolicy(ExpirationPolicy.CREATED)
            .expirationListener{ key:String, value:Any ->
                log.info { "key: $key, value: $value expired" } // 유효기간 지나면 알아서 사라짐
            }
            .maxSize(100000000)
            .build()
    // 서버가 여러 개일 때, 데이터 공유가 안되기 때문에
    // 사용하는 의미가 없음
    // Redis 서버를 둠

    override fun save(key: String, value: Any) {
        Thread.sleep(50)
        store[key] = value
    }

    override fun clear() {
        store.clear()
    }

    override fun remove(key: String): Any? {
        return store.remove(key)
    }

    override fun findAll(): MutableList<Any> {
        return ArrayList<Any>(store.values)
    }

    override fun findByKey(key: String): Any {
        return Optional.ofNullable(store.get(key)).orElseThrow{
            throw RuntimeException("not found refreshToken")
        }
    }



}