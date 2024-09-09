package com.example.kotlinstudy.service.common

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * @PackageName : com.example.kotlinstudy.service.common
 * @FileName : LocalS3FileUploaderServiceImpl
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class LocalS3FileUploaderServiceImpl(
    private val amazonS3: AmazonS3
) : FileUploaderService{
    private val log = KotlinLogging.logger {  }
    private val folderPath = "src/main/resources/static/postImg"
    private val bucketName = "my-bucket"

    init {
        log.info { "amazonS3 ===> ${this.amazonS3.regionName}" }
        this.amazonS3.createBucket(this.bucketName)
    }

    override fun upload(file: MultipartFile): String {
        val uuid = UUID.randomUUID().toString()
        val fileName = "${uuid}_${file.originalFilename}"

        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = file.contentType
        objectMetadata.contentLength = file.size

        val putObjectRequest = PutObjectRequest(this.bucketName, fileName, file.inputStream, objectMetadata)

        this.amazonS3.putObject(putObjectRequest)
        val url = this.amazonS3.getUrl(this.bucketName, fileName)

        return url.toString()
    }
}