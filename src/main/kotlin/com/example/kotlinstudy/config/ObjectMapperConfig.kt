package com.example.kotlinstudy.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @PackageName : com.example.kotlinstudy.config
 * @FileName : ObjectMapperConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-02
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = JsonMapper.builder()
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)  // 알파벳 순서로 정렬
                .build()
        val javaTimeModule = JavaTimeModule()

        // 해당 모듈에 Serializer 등록해주기
        javaTimeModule.addSerializer(LocalDateTime::class.java, CustomLocalDateTimeSerializer())
        javaTimeModule.addDeserializer(LocalDateTime::class.java, CustomLocalDateTimeDeserializer())
        mapper.registerModule(javaTimeModule)

        mapper.registerModule(
                KotlinModule.Builder()
                        .configure(KotlinFeature.StrictNullChecks, false)
                        .configure(KotlinFeature.NullIsSameAsDefault, false)
                        .configure(KotlinFeature.NullToEmptyMap, false)
                        .configure(KotlinFeature.NullToEmptyCollection, false)
                        .configure(KotlinFeature.SingletonSupport, false)
                        .build()
        )
        return mapper
    }


    // LocalDateTime Formatting
    class CustomLocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
        private val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
        private val formatter = DateTimeFormatter.ofPattern(dateTimeFormat, Locale.KOREA)

        override fun serialize(value: LocalDateTime, gen: JsonGenerator, serializers: SerializerProvider) {
            gen.writeString(formatter.format(value))
        }
    }

    // LocalDateTime Formatting Deserializer
    class CustomLocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
        private val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
        private val formatter = DateTimeFormatter.ofPattern(dateTimeFormat, Locale.KOREA)

        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
            val text = p.text
            return LocalDateTime.parse(text, formatter)
        }
    }
}