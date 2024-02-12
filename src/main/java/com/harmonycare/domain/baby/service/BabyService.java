package com.harmonycare.domain.baby.service;

import com.harmonycare.domain.baby.dto.request.BabyCreateRequest;
import com.harmonycare.domain.baby.dto.response.BabyReadResponse;
import com.harmonycare.domain.baby.entity.Baby;
import com.harmonycare.domain.baby.entity.Gender;
import com.harmonycare.domain.baby.exception.BabyErrorCode;
import com.harmonycare.domain.baby.repository.BabyRepository;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.global.exception.GlobalException;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BabyService {
    private final BabyRepository babyRepository;

    @Transactional
    public Long createBaby(BabyCreateRequest requestBody, Member member) {
        Baby baby = Baby.builder()
                .name(requestBody.name())
                .gender(Gender.valueOf(requestBody.gender()))
                .birthdate(DateTimeUtil.stringToLocalDateTime(requestBody.birthDate()))
                .birthWeight(requestBody.birthWeight())
                .member(member)
                .build();

        return babyRepository.save(baby).getId();
    }

    public BabyReadResponse readBabyByBabyId(Long babyId) {
        Baby baby = babyRepository.findById(babyId)
                .orElseThrow(() -> new GlobalException(BabyErrorCode.BABY_NOT_FOUND));

        return BabyReadResponse.from(baby);
    }

    public List<BabyReadResponse> readAllBaby() {
        List<Baby> babyList = babyRepository.findAll();

        return babyList.stream()
                .map(BabyReadResponse::from)
                .collect(Collectors.toList());
    }

    public List<BabyReadResponse> readMyBaby(Member member) {
        List<Baby> babyList = babyRepository.findAllByMember(member);

        return babyList.stream()
                .map(BabyReadResponse::from)
                .collect(Collectors.toList());
    }
}
