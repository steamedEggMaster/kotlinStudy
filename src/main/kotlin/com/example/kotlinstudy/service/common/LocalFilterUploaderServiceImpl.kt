package com.example.kotlinstudy.service.common

import com.example.kotlinstudy.config.aop.CustomAopObject
import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * @PackageName : com.example.kotlinstudy.service.common
 * @FileName : LocalFilterUploaderServiceImpl
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class LocalFilterUploaderServiceImpl(

) : FileUploaderService {

    private val log = KotlinLogging.logger {  }

    val localImgFolderPath = "src/main/resources/static/postImg"
    //"/postImg" // 해당 경로는 C: 밑에 생겨서 이렇게 하면 안됨

    @PostConstruct
    fun init() = CustomAopObject.wrapTryCatchVoidFunc {

        val directory = File(localImgFolderPath)

        if(!directory.exists()){
            log.info { "create $localImgFolderPath directory" }
            Files.createDirectory(Paths.get(localImgFolderPath))
        }
    }

    override fun upload(file: MultipartFile): String {

        val uuid = UUID.randomUUID().toString()
        val fileName = "${uuid}_${file.originalFilename}"

        val imgFilePath = Paths.get(localImgFolderPath + "/" + fileName)
        // write에는 파일명까지 담는 Path가 들어가야함!
        // "/" 이거 필수!!
        Files.write(imgFilePath, file.bytes )
        val url = ResourceUtils.getURL(imgFilePath.toString())
        //file의 url을 가져오면 맨앞에 자동으로 "file:" 이 붙음

        log.info { "url ==> $url" }

        return url.toString()
    }
}