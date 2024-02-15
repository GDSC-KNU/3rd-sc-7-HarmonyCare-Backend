package com.harmonycare.domain.member.controller;

import com.harmonycare.domain.member.dto.response.ProfileReadResponse;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.domain.member.repository.MemberRepository;
import com.harmonycare.domain.member.service.MemberService;
import com.harmonycare.global.security.details.PrincipalDetails;
import com.harmonycare.global.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.harmonycare.global.util.ApiUtil.success;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;

    /**
     * 해당 member의 profile 조회
     *
     * @param principalDetails member 정보
     * @return profile 데이터 리턴
     */
    @GetMapping("/profiles")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<ProfileReadResponse>> readProfile(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        ProfileReadResponse profileReadResponse = memberService.readProfile(principalDetails.member());

        return ResponseEntity.ok().body(success(HttpStatus.OK, profileReadResponse));
    }
}
