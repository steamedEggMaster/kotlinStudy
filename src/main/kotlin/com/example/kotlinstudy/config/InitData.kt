package com.example.kotlinstudy.config

import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.member.MemberRepository
import com.example.kotlinstudy.domain.member.LoginDto
import com.example.kotlinstudy.domain.member.Role
import com.example.kotlinstudy.domain.post.Post
import com.example.kotlinstudy.domain.post.PostRepository
import com.example.kotlinstudy.domain.post.PostSaveReq
import com.example.kotlinstudy.domain.post.toEntity
import io.github.serpro69.kfaker.faker
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

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

        private val memberRepository: MemberRepository,
        private val postRepository: PostRepository

) {

    val faker = faker {  }
    private val log = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    private fun init(){

//        val members = generateMembers(100)
//        memberRepository.saveAll(members)
//        val posts = generatePosts(100)
//        postRepository.saveAll(posts)
    }

    private fun generateMembers(cnt:Int): MutableList<Member> {
        val members = mutableListOf<Member>()

        for (i in 1..100){
            val member = generateMember()
            log.info { "hello ${member.toString()}" }
            members.add(member)
        }
        return members
    }

    private fun generatePosts(cnt: Int): MutableList<Post> {
        val posts = mutableListOf<Post>()

        for(i in 1..100){
            val post = generatePost()
            log.info { "hello ${post.toString()}" }
            posts.add(post)
        }
        return posts
    }

    private fun generateMember(): Member = LoginDto(
            email = faker.internet.safeEmail(),
            rawPassword = "1234",
            role = Role.USER
    ).toEntity()

    private fun generatePost(): Post = PostSaveReq(
            title = faker.theExpanse.ships(),
            content = faker.quote.famousLastWords(),
            memberId = 1,
    ).toEntity()

}