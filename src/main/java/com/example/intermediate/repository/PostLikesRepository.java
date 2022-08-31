package com.example.intermediate.repository;

import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    Optional<PostLikes> findByPostId(Long postId);

    void deleteByMemberAndPostId(Member member, Long postId);

    List<PostLikes> findAllByPostId(Long postId);

    Optional<PostLikes> findByMemberAndPostId(Member member, Long postId);


}
