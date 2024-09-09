package com.example.kotlinstudy.service

import com.example.kotlinstudy.domain.comment.CommentRepository
import com.example.kotlinstudy.domain.comment.CommentSaveReq
import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.post.Post
import com.example.kotlinstudy.domain.post.PostRepository
import com.example.kotlinstudy.setup.MockitoHelper
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : ServiceTest
 * @Author : noglass_gongdae
 * @Date : 2024-09-08
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@ExtendWith(MockitoExtension::class)
class ServiceTest {
    private val log = KotlinLogging.logger {  }

    @Mock // mocking 용 객체, 테스트 런타임 시 주입됨
    // 테스트 시 부가적으로 필요한 DB나 JPA 관련 Bean 등 매번 메모리에 띄우기 부담스러움
    // -> 가볍게 띄워 해당 메서드의 로직만을 테스트 가능하게 하는 Bean 껍데기만을 주입해줌
    // 실제 구현체가 아님
    // -> 해당 Bean의 메서드들의 return 값을 지정을 해주어야 함
    private lateinit var commentRepository: CommentRepository

    @Mock
    private lateinit var postRepository: PostRepository

    @InjectMocks // @Mocking 용 객체를 주입받을 클래스
    private lateinit var commentService: CommentService

    @Test
    fun mockDiTest(){ // DI 확인용
        log.info { """
            ${this.commentService}
        """.trimIndent() }
    }

    @Test
    fun saveCommentTest() {

        // given
        val dto = CommentSaveReq(
                memberId = 1,
                content = "test content",
                postId = 1,
                idAncestor = null
        )

        val post = Optional.ofNullable(Post(
                id = 1,
                title = "title",
                content = "content",
                member = Member.createFakeMember(1)
        ))

        val expectedPost = post.orElseThrow()
        val comment = dto.toEntity(expectedPost)

        //stub
        Mockito.`when`(postRepository.findById(dto.postId)).thenReturn(post)
        Mockito.`when`(commentRepository.saveComment(MockitoHelper.anyObject())).thenReturn(comment)
        Mockito.`when`(commentRepository.saveCommentClosure(anyLong(), eq(dto.idAncestor))).thenReturn(anyInt())

        val saveComment = commentService.saveComment(dto)

        //then
        Assertions.assertEquals(comment.content, saveComment.content)
        assertEquals(comment.post.member.id, saveComment.member.id)
    }
}