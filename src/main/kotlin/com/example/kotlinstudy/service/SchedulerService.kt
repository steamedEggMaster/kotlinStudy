package com.example.kotlinstudy.service

import com.example.kotlinstudy.config.scheduler.MyCronExpression
import com.example.kotlinstudy.domain.post.PostRepository
import com.example.kotlinstudy.event.PostDeleteAtUpdateEvent
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : SchedulerService
 * @Author : noglass_gongdae
 * @Date : 2024-09-09
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class SchedulerService(
        private val eventPublisher: ApplicationEventPublisher,
) {

    private val log = KotlinLogging.logger {  }


    @Transactional
    //@Scheduled(initialDelay = 1000, fixedRate = 1000)
    @Scheduled(cron = MyCronExpression.oneMinute)
    fun schedulerTest(){
        log.info { "schedulerTest" }
        eventPublisher.publishEvent(PostDeleteAtUpdateEvent(LocalDateTime.now()))
    }
}