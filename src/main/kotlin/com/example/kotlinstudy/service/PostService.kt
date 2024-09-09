package com.example.kotlinstudy.service

import com.example.kotlinstudy.domain.post.*
import com.example.kotlinstudy.event.PostDeleteAtUpdateEvent
import com.example.kotlinstudy.exception.PostNotFoundException
import com.example.kotlinstudy.service.common.LocalS3FileUploaderServiceImpl
import com.example.kotlinstudy.util.dto.SearchCondition
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : Postservice
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class PostService(
        private val postRepository: PostRepository,
        private val localS3FileUploaderServiceImpl: LocalS3FileUploaderServiceImpl,
        private val cacheService: CacheService
) {

    private val log = KotlinLogging.logger {  }

    //@PreAuthorize("hasAuthority('SUPER')") // SecurityCnfig의 @EnableMethodSecurity와 함께 사용되는 어노테이션
    // 메서드를 제한함!
    @Transactional(readOnly = true)
    fun findPosts(pageable: Pageable, searchCondition: SearchCondition): Page<PostRes> {

        return postRepository.findPosts(pageable, searchCondition).map { it.toDto() }

    }

    @Transactional
    fun savePost(dto: PostSaveReq): PostRes {
        return postRepository.save(dto.toEntity()).toDto()
    }

    @Transactional
    fun deletePost(id : Long){
        return postRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun findPostById(id:Long): PostRes {
        return postRepository.findById(id).orElseThrow { throw PostNotFoundException(id.toString()) }.toDto()
    }

    fun savePostImg(image: MultipartFile): String {
        return localS3FileUploaderServiceImpl.upload(image)
    }

    // 매번 Repo에 접근하기 그러니, 캐시에 저장해두고 빠르게 가져오기 위함
    //@Cacheable(cacheNames = [ CacheType.postName ], key = "'fixedKey'") // CacheType enum 클래스의 cache name 값
    // 캐싱 주의점
    // 1. key는 파라미터를 통해 자동으로 완성되어짐 => 파라미터가 다르면 캐시가 무효화됨
    // 2. 파라미터가 없다면, key는 빈 문자열로 정해짐
    // => key 옵션을 통해 key를 고정시키자!!
    // key옵션 : SpEL(Spring Expression Language)로 써주기
    // => 항상 같은 값이 나옴;;;;;
    @Transactional(readOnly = true)
    fun autoCompletePostTitle(word: String): MutableList<String> {

        val complete = cacheService.addAutoCompletePostTitle().complete(word)
        val searchWords = mutableListOf<String>()

        for (autocompleteData in complete) {
            searchWords.add(autocompleteData.value)
        }

        return searchWords
    }

//    @Cacheable(cacheNames = [ CacheType.postName ], key = "'fixedKey'")
//    // 이렇게 메서드로 바깥으로 빼도 캐싱되지 않음
//    // why???? 캐시 추상화는 DynamicFroxy를 활용함
//    // => 클래스 내의 자가 메서드 호출에는 적용되지 않음
//    // => 다른 클래스에 써주자!!
//    fun addAutoCompletePostTitle() {
//        postRepository.findAll().forEach {
//            autocompleteRepository.add(it.title)
//        }
//    }

    @EventListener
    @Transactional
    fun updateDeleteAtEventHandler(event: PostDeleteAtUpdateEvent) {

        log.info { "event 구독 ==> $event" }

        val allPosts = postRepository.findAll()
        val filterPosts = allPosts.filter {
            LocalDateTime.now().isAfter(it.reserveAt)
        }

        val ids = filterPosts.map {
            it.id
        }

        val count = postRepository.updateDeleteAtByReserveAt(ids)

        log.info { "update 공개 처리된 갯수 : $count" }
    }

}