package com.example.kotlinstudy.exception

/**
 * @PackageName : com.example.kotlinstudy.exception
 * @FileName : EntityNotFoundException
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
sealed class EntityNotFoundException(message:String?) : BusinessException(message, ErrorCode.ENTITY_NOT_FOUND) {



}

class MemberNotFoundException(id:Long) : EntityNotFoundException("$id not found")