package com.example.intermediate.controller;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.PostService;
import com.example.intermediate.service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImgUploadController {

    private final S3Upload s3Upload;
    private final PostService postService;

    @PostMapping(value = "/upload/{id}")
    public ResponseDto<?> uploadFile(@RequestParam("images") MultipartFile multipartFile,
                                     @RequestParam String fileSize,@PathVariable Long id, MultipartHttpServletRequest request) throws IOException {

        return postService.updateImage(id,multipartFile,fileSize,request);
    }
}
