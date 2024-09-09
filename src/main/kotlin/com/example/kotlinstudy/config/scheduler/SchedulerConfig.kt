package com.example.kotlinstudy.config.scheduler

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

/**
 * @PackageName : com.example.kotlinstudy.config.jpa
 * @FileName : SchedulerConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-09
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@EnableScheduling
@Configuration
class SchedulerConfig(

) {

    private val log = KotlinLogging.logger {  }
    private val schedulerPoolSize = 10
    private val schedulerNamePrefix = "kang"

    @Bean
    fun threadPoolTaskScheduler(): ThreadPoolTaskScheduler {
        val taskScheduler = ThreadPoolTaskScheduler()
        taskScheduler.poolSize = schedulerPoolSize
        taskScheduler.setThreadNamePrefix(schedulerNamePrefix)
        taskScheduler.initialize()

        return taskScheduler
    }

}