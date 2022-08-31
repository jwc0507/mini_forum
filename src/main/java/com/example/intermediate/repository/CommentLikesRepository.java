package com.example.intermediate.repository;

import com.example.intermediate.domain.CommentLikes;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {

    Optional<CommentLikes> findByCommentId(Long commentId);

    void deleteByMemberAndCommentId(Member member, Long commentId);

    List<CommentLikes> findAllByCommentId(Long commentId);

    Optional<CommentLikes> findByMemberAndCommentId(Member member, Long commentId);


}
