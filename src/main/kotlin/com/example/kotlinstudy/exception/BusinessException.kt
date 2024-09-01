package com.example.kotlinstudy.exception

/**
 * @PackageName : com.example.kotlinstudy.exception
 * @FileName : BusinessException
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
sealed class BusinessException : RuntimeException {

    private var errorCode:ErrorCode
        get() {
            return this.errorCode
        }

    constructor(errorCode: ErrorCode):super(errorCode.message){
        this.errorCode = errorCode
    }

    constructor(message: String?, errorCode: ErrorCode):super(message){
        this.errorCode = errorCode
    }
}