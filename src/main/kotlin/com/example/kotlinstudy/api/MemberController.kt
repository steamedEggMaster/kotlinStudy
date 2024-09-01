package com.example.kotlinstudy.api

import com.example.kotlinstudy.domain.member.LoginDto
import com.example.kotlinstudy.service.MemberService
import com.example.kotlinstudy.util.value.CmResDto
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * @PackageName : com.example.kotlinstudy.api
 * @FileName : MemberController
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RequestMapping("/v1")
@RestController
class MemberController(
        private var memberService: MemberService
) {

    @GetMapping("/members")
    fun findAll(@PageableDefault(size = 10) pageable: Pageable, session: HttpSession): CmResDto<*> {

        return CmResDto(HttpStatus.OK, "find All Members", memberService.findAll(pageable))
    }

    @GetMapping("/member/{id}")
    fun findById(@PathVariable id:Long): CmResDto<Any> {
        return CmResDto(HttpStatus.OK, "find Member by id", memberService.findMemberById(id))
    }

    @DeleteMapping("/member/{id}")
    fun deleteById(@PathVariable id:Long): CmResDto<Any> {
        return CmResDto(HttpStatus.OK, "delete Member by id", memberService.deleteMember(id))
    }

    @PostMapping("/member")
    fun save(@Valid @RequestBody dto:LoginDto): CmResDto<*> {
        return CmResDto(HttpStatus.OK, "save Member", memberService.saveMember(dto))

    }
}