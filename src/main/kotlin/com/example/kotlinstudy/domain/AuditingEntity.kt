package com.example.kotlinstudy.domain

import com.example.kotlinstudy.util.dto.BaseDto
import jakarta.persistence.*
import mu.KotlinLogging
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @PackageName : com.example.kotlinstudy.domain
 * @FileName : AuditingEntity
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class AuditingEntity(
    id:Long
) : AuditingEntityId(id) {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt:LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt:LocalDateTime = LocalDateTime.now()
        protected set

    @Column(name = "deleted_at")
    var deletedAt:LocalDateTime? = null
        protected set

     fun closedEntity(){
        this.deletedAt = LocalDateTime.now()

        log.info { "${this::class.java.name} : 비공개 처리 : ${this.deletedAt}" }
    }

    protected fun setBaseDtoProperty(dto: BaseDto) {
        dto.id = this.id
        dto.createdAt = this.createdAt
        dto.updatedAt = this.updatedAt
    }

    companion object {
        private val log = KotlinLogging.logger {  }
    }

}

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class AuditingEntityId(
        id: Long
) : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = id
        protected set
}