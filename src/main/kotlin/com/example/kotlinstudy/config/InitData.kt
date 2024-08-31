package com.example.kotlinstudy.config

import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.member.MemberRepository
import com.example.kotlinstudy.domain.member.Role
import io.github.serpro69.kfaker.faker
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

/**
 * @PackageName : com.example.kotlinstudy.config
 * @FileName : InitData
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class InitData(

        private val memberRepository: MemberRepository

) {

    val faker = faker {  }
    private val log = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    private fun init(){


        val member = Member(
                email = faker.internet.safeEmail(),
                password = "1234",
                role = Role.USER
        )

        log.info { "hello ${member.toString()}" }

        memberRepository.save(member)

    }

}