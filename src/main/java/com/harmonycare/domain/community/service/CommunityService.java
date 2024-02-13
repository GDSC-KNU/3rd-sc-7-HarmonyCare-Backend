package com.harmonycare.domain.community.service;

import com.harmonycare.domain.community.dto.request.CommunityWriteRequest;
import com.harmonycare.domain.community.dto.response.CommunityReadResponse;
import com.harmonycare.domain.community.entity.Community;
import com.harmonycare.domain.community.exception.CommunityErrorCode;
import com.harmonycare.domain.community.repository.CommunityRepository;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    @Transactional
    public Long writeCommunity(Member member, CommunityWriteRequest requestBody) {
        Community community = communityRepository.save(requestBody.toEntity(member));

        return community.getId();
    }

    public List<CommunityReadResponse> readAllCommunity() {
        List<Community> communityList = communityRepository.findAll();

        return communityList.stream()
                .map(CommunityReadResponse::from)
                .toList();
    }

    public List<CommunityReadResponse> readAllMyCommunity(Member member) {
        List<Community> communityList = communityRepository.findAllByMember(member);

        return communityList.stream()
                .map(CommunityReadResponse::from)
                .toList();

    }

    @Transactional
    public void deleteCommunity(Long communityId) {
        if (!communityRepository.existsById(communityId))
            throw new GlobalException(CommunityErrorCode.COMMUNITY_NOT_FOUND);

        communityRepository.deleteById(communityId);
    }
}
