package com.example.intermediate.service;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.*;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.CommentLikesRepository;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.PostLikesRepository;
import com.example.intermediate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class likeService {

    private final TokenProvider tokenProvider;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final PostLikesRepository postLikesRepository;

    private final CommentLikesRepository CommentLikesRepository;

    public ResponseDto<?> postLikes(Long postId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        post.updateLikeCount("up");
        if(postLikesRepository.findByMemberAndPostId(member, postId).isEmpty()) {
            PostLikes postLikes = PostLikes.builder()
                    .postId(postId)
                    .member(member)
                    .isPostlikes(true)
                    .build();
            postLikesRepository.save(postLikes);

        }else{
            return ResponseDto.fail("LIKE_TRUE","이미 like 상태입니다.");
        }

        return ResponseDto.success("like success");

    }

    public ResponseDto<?> postLikeDelete(Long postId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }

        if(postLikesRepository.findByMemberAndPostId(member, postId).isPresent()){
            postLikesRepository.deleteByMemberAndPostId(member, postId);
            post.updateLikeCount("down");
        }else{
            return ResponseDto.fail("LIKE_FALSE","like 상태가 아닙니다.");
        }

        return ResponseDto.success("like delete success");


    }

    public ResponseDto<?> commentLikes(Long commentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Comment comment = isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        if(CommentLikesRepository.findByMemberAndCommentId(member, commentId).isEmpty()) {
            CommentLikes commentLikes = CommentLikes.builder()
                    .commentId(commentId)
                    .member(member)
                    .isCommentlikes(true)
                    .build();
            CommentLikesRepository.save(commentLikes);

        }else{
            return ResponseDto.fail("LIKE_TRUE","이미 like 상태입니다.");
        }
        comment.updateLikeCount("up");
        return ResponseDto.success("like success");


    }

    public ResponseDto<?> commentLikeDelete(Long commentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }

        if(CommentLikesRepository.findByMemberAndCommentId(member, commentId).isPresent()){
            comment.updateLikeCount("down");
            CommentLikesRepository.deleteByMemberAndCommentId(member, commentId);
        }else{
            return ResponseDto.fail("LIKE_FALSE","like 상태가 아닙니다.");
        }

        return ResponseDto.success("like delete success");

    }

//    public ResponseDto<?> subCommentLikes(Long subComment_id, HttpServletRequest request) {
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        SubComment subComment = isPresentSubComment(subComment_id);
//        if (null == subComment) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
//        }
//
//        Member member = validateMember(request);
//        if (null == member) {
//            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//
//        if(member.isPostLikes()==false) {
//            post.sumPostLikes(post_id);
//            member.setPost_id(post_id);
//            member.setPostLikes(true);
//        }else{
//            return ResponseDto.fail("LIKE_TRUE", "이미 LIKE_TRUE 상태입니다.");
//        }
//        return ResponseDto.success(
//                LikesResponseDto.builder()
//                        .likes(member.isPostLikes()));
//    }
//
//    public ResponseDto<?> subCommentLikeDelete(Long subComment_id, HttpServletRequest request) {
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        Member member = validateMember(request);
//        if (null == member) {
//            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//
//        SubComment subComment = isPresentSubComment(subComment_id);
//        if (null == subComment) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
//        }
//
//        if (subcomment.validateMember(member)) {
//            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
//        }
//
//        if(member.isPostLikes()==true) {
//            post.deletePostLikes(post_id);
//            member.setPost_id(post_id);
//            member.setPostLikes(false);
//        }else{
//            return ResponseDto.fail("LIKE_FALSE", "이미 LIKE_FALSE 상태입니다.");
//        }
//        return ResponseDto.success(
//                LikesResponseDto.builder()
//                        .likes(member.isPostLikes()));
//    }


    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
