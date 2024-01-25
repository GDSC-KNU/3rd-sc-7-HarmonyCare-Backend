package com.harmonycare.domain.member.service;

import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.domain.member.exception.MemberErrorCode;
import com.harmonycare.domain.member.repository.MemberRepository;
import com.harmonycare.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
