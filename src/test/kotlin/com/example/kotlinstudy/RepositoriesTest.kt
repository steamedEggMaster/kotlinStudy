package com.example.kotlinstudy

import com.example.kotlinstudy.domain.post.PostRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactoryImpl
import jakarta.persistence.EntityManager
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import kotlin.math.E

/**
 * @PackageName : com.example.kotlinstudy
 * @FileName : RepositoriesTest
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@DataJpaTest
@AutoConfigureTestDatabase
// Jpa 관련 빈들만 올려 가볍게 Test
class RepositoriesTest {

    private val log = KotlinLogging.logger {  }

    @Autowired
    private lateinit var postRepository: PostRepository

    @Test
    fun setupTest(){
        log.info { "setUp!!!!" }
    }

//    @TestConfiguration
//    class TestConfig(
//            @Autowired
//            private val entityManager: EntityManager
//    ){
//
//        @Bean
//        fun springDataQueryFactory(): SpringDataQueryFactoryImpl {
//            return SpringDataQueryFactoryImpl(
//                    criteriaQueryCreator = CriteriaQueryCreatorImpl(entityManager),
//                    SubqueryCreatorImpl()
//            )
//        }
//    }

}