package com.example.intermediate.repository;

import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.PostLikes;
import com.example.intermediate.domain.SubCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCommentLikesRepository extends JpaRepository<SubCommentLikes, Long> {

    Optional<SubCommentLikes> findByMemberAndSubCommentId(Member member, Long subCommentId);

    @Override
    void deleteById(Long likesId);

}
