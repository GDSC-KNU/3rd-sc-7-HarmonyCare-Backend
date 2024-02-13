package com.harmonycare.domain.community.service;

import com.harmonycare.domain.community.dto.request.CommentWriteRequest;
import com.harmonycare.domain.community.dto.request.CommunityWriteRequest;
import com.harmonycare.domain.community.dto.response.CommentReadResponse;
import com.harmonycare.domain.community.dto.response.CommunityReadResponse;
import com.harmonycare.domain.community.entity.Comment;
import com.harmonycare.domain.community.entity.Community;
import com.harmonycare.domain.community.exception.CommentErrorCode;
import com.harmonycare.domain.community.exception.CommunityErrorCode;
import com.harmonycare.domain.community.repository.CommentRepository;
import com.harmonycare.domain.community.repository.CommunityRepository;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public Long writeComment(Member member, CommentWriteRequest requestBody) {
        Community community = communityRepository.findById(requestBody.communityId())
                .orElseThrow(() -> new GlobalException(CommunityErrorCode.COMMUNITY_NOT_FOUND));

        Comment comment = commentRepository.save(requestBody.toEntity(member, community));

        return comment.getId();
    }

    public List<CommentReadResponse> readCommentByCommunityId(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new GlobalException(CommunityErrorCode.COMMUNITY_NOT_FOUND));

        List<Comment> commentList = commentRepository.findAllByCommunity(community);

        return commentList.stream()
                .map(CommentReadResponse::from)
                .toList();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new GlobalException(CommentErrorCode.COMMENT_NOT_FOUND);

        commentRepository.deleteById(commentId);
    }
}
