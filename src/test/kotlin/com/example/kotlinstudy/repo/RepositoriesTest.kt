package com.example.kotlinstudy.repo

import com.example.kotlinstudy.config.P6spyPrettySqlFormatter
import com.example.kotlinstudy.domain.post.PostRepository
import com.example.kotlinstudy.util.dto.SearchCondition
import com.example.kotlinstudy.util.dto.SearchType
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactoryImpl
import com.p6spy.engine.spy.P6SpyOptions
import jakarta.persistence.EntityManager
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageRequest

/**
 * @PackageName : com.example.kotlinstudy
 * @FileName : RepositoriesTest
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
                        // @AutoConfigureTestDatabase는 @DataJpaTest에 기본적으로 있고,
                        // 자동으로 내장된 DB로 바꿔 테스트함.
                        // 실제 DB로 테스트 하고 싶다면 위의 설정을 해줄 것
// Jpa 관련 빈들만 올려 가볍게 Test
class RepositoriesTest {

    private val log = KotlinLogging.logger {  }

    @Autowired
    private lateinit var postRepository: PostRepository

    @Test
    fun setupTest(){
        log.info { "setUp!!!!" }
    }

    @Test
    fun jdslDynamicQueryTest(){
        val (pageable, condition) = pageRequestSearchConditionPair()

        val posts = postRepository.findPosts(pageable = pageable, searchCondition = condition).totalElements

        Assertions.assertThat(posts).isEqualTo(5)
    }

    private fun pageRequestSearchConditionPair(): Pair<PageRequest, SearchCondition> {
        val pageable = PageRequest.of(0, 100)
        val condition = SearchCondition(
                searchType = SearchType.TITLE,
                keyword = "Nephthys"
        )
        return Pair(pageable, condition)
    }

    @TestConfiguration
    class TestConfig(
    //Kotlin은 inner 클래스 생성시 자동으로
    //public static으로 만들어줌
            @Autowired
            private val entityManager: EntityManager
    ){

        @Bean
        fun springDataQueryFactory(): SpringDataQueryFactoryImpl {

            P6SpyOptions.getActiveInstance().logMessageFormat = P6spyPrettySqlFormatter::class.java.name

            return SpringDataQueryFactoryImpl(
                    criteriaQueryCreator = CriteriaQueryCreatorImpl(entityManager),
                    SubqueryCreatorImpl()
            )
        }
    }

}