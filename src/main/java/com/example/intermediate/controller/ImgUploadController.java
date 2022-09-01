package com.example.intermediate.controller;

import com.example.intermediate.service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImgUploadController {

    private final S3Upload s3Upload;

    @PostMapping(value = "/upload",produces = "image/png")
    public String uploadFile(@RequestParam("images") MultipartFile multipartFile,
                               @RequestParam String fileSize) throws IOException {


        System.out.println("1 : "+ HttpStatus.CREATED);
        System.out.println("2 : "+ multipartFile.getInputStream());
        System.out.println("3 : "+ multipartFile.getOriginalFilename());
        System.out.println("4 : "+ fileSize);
        String imageUrl=s3Upload.upload(multipartFile.getInputStream(),multipartFile.getOriginalFilename(),fileSize);

        return imageUrl;
    }
}
