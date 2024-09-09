package com.example.kotlinstudy.service

import com.example.kotlinstudy.domain.comment.Comment
import com.example.kotlinstudy.domain.comment.CommentRepository
import com.example.kotlinstudy.domain.comment.CommentRes
import com.example.kotlinstudy.domain.comment.CommentSaveReq
import com.example.kotlinstudy.domain.post.PostRepository
import com.example.kotlinstudy.exception.PostNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : CommentService
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class CommentService(
    private val commentRepository: CommentRepository,
        private val postRepository: PostRepository
) {
    private val log = KotlinLogging.logger {  }

    @Transactional(readOnly = true)
    fun saveComment(dto:CommentSaveReq): CommentRes {
        val post = postRepository.findById(dto.postId).orElseThrow{throw PostNotFoundException(dto.postId.toString())}
        val comment :Comment = commentRepository.saveComment(dto.toEntity(post = post))

        commentRepository.saveCommentClosure(comment.id, dto.idAncestor)

        return comment.toDto()
    }

    @Transactional(readOnly = true)
    fun findCommentByAncestorComment(idAncestor:Long): List<CommentRes> {
        return commentRepository.findCommentByAncestorComment(idAncestor).map { it.toDto() }
    }
}