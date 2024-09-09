package com.example.kotlinstudy.config.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.findify.s3mock.S3Mock
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @PackageName : com.example.kotlinstudy.config.aws
 * @FileName : EmbeddedS3Config
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class EmbeddedS3Config {

    private val log = KotlinLogging.logger {  }
    val port = 7777
    private val api = S3Mock.Builder().withPort(port).withInMemoryBackend().build()
    private val serviceEndpoint = "http://localhost:7777"
    private val region = "us-west-2"

    @PostConstruct
    fun init() {
        log.info { "s3 Mock API Start" }

        this.api.start()
    }

    @PreDestroy
    fun destroy(){
        log.info {"s3 Mock API ShutDown"}

        this.api.shutdown()
    }

    @Bean
    fun amazonS3(): AmazonS3 {
        val endpoint = EndpointConfiguration(serviceEndpoint, region)
        val client: AmazonS3 = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(AWSStaticCredentialsProvider(AnonymousAWSCredentials()))
                .build()

        log.info {"embeddedAmazonS3 ===> ${client}"}

        return client
    }

}