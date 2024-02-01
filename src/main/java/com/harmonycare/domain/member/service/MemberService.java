package com.harmonycare.domain.member.service;

import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.domain.member.entity.Role;
import com.harmonycare.domain.member.exception.MemberErrorCode;
import com.harmonycare.domain.member.repository.MemberRepository;
import com.harmonycare.global.exception.GlobalException;
import com.harmonycare.global.util.OauthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public boolean existMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void saveMember(String email) {
        memberRepository.save(Member.builder()
                .email(email)
                .password(passwordEncoder.encode(OauthUtil.oauthPasswordKey))
                .role(Role.ROLE_USER)
                .build());
    }
}
