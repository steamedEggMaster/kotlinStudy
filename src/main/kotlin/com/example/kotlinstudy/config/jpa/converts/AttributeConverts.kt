package com.example.kotlinstudy.config.jpa.converts

import com.example.kotlinstudy.domain.post.PostType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * @PackageName : com.example.kotlinstudy.config.jpa.converts
 * @FileName : AttributeConverts
 * @Author : noglass_gongdae
 * @Date : 2024-09-09
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Converter
class PostTypeConverter : AttributeConverter<PostType, String> {
    override fun convertToDatabaseColumn(attribute: PostType?): String? {
        return attribute?.name
    }

    override fun convertToEntityAttribute(dbData: String?): PostType {
        return PostType.ofCode(dbData)
    }

}