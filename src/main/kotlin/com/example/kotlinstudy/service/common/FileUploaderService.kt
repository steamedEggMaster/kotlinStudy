package com.example.kotlinstudy.service.common

import org.springframework.web.multipart.MultipartFile

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : FileUploaderService
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
interface FileUploaderService {
    fun upload(file: MultipartFile): String
}