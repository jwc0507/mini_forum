package com.example.intermediate.controller;


import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.likeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
public class LikesController {

    private final likeService likeService;

    @RequestMapping(value = "/api/auth/like/post/{postId}", method = RequestMethod.POST)
    public ResponseDto<?> postLikes(@PathVariable Long postId, HttpServletRequest request) {
        return likeService.postLikes(postId, request);
    }

    @RequestMapping(value = "/api/auth/like/post/{posId}", method = RequestMethod.DELETE)
    public ResponseDto<?> postLikeDelete(@PathVariable Long posId, HttpServletRequest request) {
        return likeService.postLikeDelete(posId, request);
    }

    @RequestMapping(value = "/api/auth/like/comment/{commentId}", method = RequestMethod.POST)
    public ResponseDto<?> commentLikes(@PathVariable Long commentId, HttpServletRequest request) {
        return likeService.commentLikes(commentId, request);
    }

    @RequestMapping(value = "/api/auth/like/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseDto<?> commentLikeDelete(@PathVariable Long commentId, HttpServletRequest request) {
        return likeService.commentLikeDelete(commentId, request);
    }

//    @RequestMapping(value = "/api/auth/like/subComment/{subComment_id}", method = RequestMethod.POST)
//    public ResponseDto<?> subCommentLikes(@PathVariable Long subComment_id, HttpServletRequest request) {
//        return likeService.subCommentLikes(subComment_id, request);
//    }
//
//    @RequestMapping(value = "/api/auth/like/subComment/{subComment_id}", method = RequestMethod.DELETE)
//    public ResponseDto<?> subCommentLikeDelete(@PathVariable Long subComment_id, HttpServletRequest request) {
//        return likeService.subCommentLikeDelete(subComment_id, request);
//    }


}
