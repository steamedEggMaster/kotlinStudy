package com.example.kotlinstudy.repo

import com.example.kotlinstudy.domain.HashMapRepositoryImpl
import com.example.kotlinstudy.domain.InMemoryRepository
import com.example.kotlinstudy.domain.RedisRepositoryImpl
import com.example.kotlinstudy.setup.SecurityConfig
import com.example.kotlinstudy.setup.TestRedisConfiguration
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @PackageName : com.example.kotlinstudy.repo
 * @FileName : RedisRepositoryImpl
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

//@Import(TestRedisConfiguration::class, SecurityConfig::class)
//@SpringBootTest // 통합테스트용
@DataRedisTest // SpringBootTest는 너무 무거우니 Redis 관련 기능들만 로드하는 어노테이션
@Import(TestRedisConfiguration::class, SecurityConfig::class)
// DataRedisTest 사용시 redis 관련 Configuration을 구성한 클래스들을 Import 해주기
class RedisRepositoryImpl(


) {
    @Autowired
    private lateinit var redisRepositoryImpl: RedisRepositoryImpl

    private val log = KotlinLogging.logger {  }

    @Test
    fun setup(){
        log.info { "test setup ==> ${this.redisRepositoryImpl}" }
    }

    @Test
    fun redisRepoTest(){

        val numberOfThreads = 1000

        val service = Executors.newFixedThreadPool(10)
        val latch = CountDownLatch(numberOfThreads)

        for (index in 1..numberOfThreads){
            service.submit {
                this.redisRepositoryImpl.save(index.toString(), index)
                latch.countDown()
            }
            // this.redisRepositoryImpl.save(index.toString(), index)
            // 2번째 매개변수에 Int 타입을 넣었을때, 값이 원하는 대로 잘 나오지 않은 이유
            // : redis는 데이터를 저장시, 기본적으로 String 기반 직렬화 방식 사용
            //   -> index.toString() 만 사용 시, StringRedisSerializer 가 아닌 자바 직렬화 방식이 사용됨
            //   -> 성능도 떨어지고, 바이너리 형식으로 변환되어 알아볼 수 없는 문자가 됨
            //   -> 즉, StringRedisSerializer 이걸 해주자!
        }



        latch.await()
        Thread.sleep(1000)

        val results = this.redisRepositoryImpl.findAll()
        Assertions.assertThat(results.size).isEqualTo(numberOfThreads)
    }



}