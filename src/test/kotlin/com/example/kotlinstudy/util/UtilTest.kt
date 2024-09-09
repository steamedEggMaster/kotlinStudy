package com.example.kotlinstudy.util

import com.example.kotlinstudy.service.common.FileUploaderService
import com.example.kotlinstudy.service.common.LocalFilterUploaderServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @PackageName : com.example.kotlinstudy.util
 * @FileName : UtilTest
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

class UtilTest {

    private val log = KotlinLogging.logger {  }

    val mapper = ObjectMapper()

    @Test
    fun localFileUploaderTest(){

        val fileUploader: FileUploaderService = LocalFilterUploaderServiceImpl()

        val path = Paths.get("src/test/resources/test/docker.jpg")
        val name = "docker.jpg"
        val originalFileName = "docker.jpg"

        val contentType = MediaType.IMAGE_JPEG_VALUE
        val content = Files.readAllBytes(path)
        val result:MultipartFile = MockMultipartFile(
                name,
                originalFileName,
                contentType,
                content
        )
        fileUploader.upload(result)
    }

}