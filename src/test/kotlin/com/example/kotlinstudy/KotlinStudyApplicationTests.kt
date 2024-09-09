package com.example.kotlinstudy

import com.example.kotlinstudy.domain.comment.CommentSaveReq
import com.example.kotlinstudy.service.CacheService
import com.example.kotlinstudy.service.CommentService
import com.example.kotlinstudy.setup.TestRedisConfiguration
import mu.KotlinLogging
import net.okihouse.autocomplete.repository.AutocompleteRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest(classes = [ TestRedisConfiguration::class ])
class KotlinStudyApplicationTests(
        @Autowired //반드시 붙여야 함
        val df: DefaultListableBeanFactory
) {

    private val log = KotlinLogging.logger {  }

    @Autowired
    private lateinit var cacheManager:CacheManager

    @Autowired
    private lateinit var cacheService: CacheService

    @Autowired
    private lateinit var commentService: CommentService

    @Autowired
    private lateinit var autocompleteRepository: AutocompleteRepository

    @Test
    fun contextLoads() {
    }

    @Test
    fun cacheManagerTest(){

        cacheService.addAutoCompletePostTitle() // 해당 함수를 한번 실행해야 key가 정의됨

        cacheManager.cacheNames.map {cacheName ->
            log.info { "cacheName ==> $cacheName" }
            val caffeineCache = cacheManager.getCache(cacheName) as CaffeineCache
            caffeineCache.nativeCache
        }.forEach {cache ->
            log.info { "cache : ${cache.asMap().keys}" }
            cache.asMap().keys.forEach {
                log.info { """
                    key ==> $it
                    value ==> ${cache.getIfPresent(it).toString()}
                """.trimIndent() }
            }
        }
    }

    @Test
    fun autoCompleteTest(){
        val apple = "apple"

        // step 1. Clear a "apple"
//        autocompleteRepository.clear(apple)
        // step 2. Add a "apple"
        autocompleteRepository.add(apple)
        // step 3. Get auto-complete words with prefix "a"
        val autocompletes = autocompleteRepository.complete("a")

        assertNotNull(autocompletes)
        assertTrue(autocompletes.size == 1)

        val autocompleteData = autocompletes[0]

        assertTrue(autocompleteData.value == apple)
        assertTrue(autocompleteData.score == 1)
    }

    @Test
    fun saveCommentTest(){

        val dto = CommentSaveReq(
                memberId = 2,
                content = "test content5",
                postId = 1,
                idAncestor = 5
        )
        commentService.saveComment(dto)

        // 해당 댓글의 대댓글들을 전부 찾는 쿼리
        // 1. 바텀업
        // SELECT c.*
        // FROM Comment AS c
        // JOIN Comment_closure AS cc
        // ON c.id = cc.id_ancestor
        // WHERE cc.id_descendant = 3;

        // 2. 탑다운
        // SELECT c.*
        // FROM Comment AS c
        // JOIN Comment_closure AS cc
        // ON c.id = cc.id_descendant
        // WHERE cc.id_ancestor = 3;
    }
}
