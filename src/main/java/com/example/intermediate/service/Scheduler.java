package com.example.intermediate.service;

import com.example.intermediate.domain.Post;
import com.example.intermediate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
public class Scheduler {
    private final PostRepository postRepository;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "30 * * * * *")
    @Transactional
    public void AutoDel() throws InterruptedException {
        List<Post> postList = postRepository.findAll();
        for (int i = 0; i < postList.size(); i++) {
        if(postList.get(i).getCommetnCount()==0){
            postRepository.delete(postList.get(i));
            System.out.println("게시물 <"+postList.get(i).getTitle()+">이 삭제되었습니다.");
        }
        }
        }
}

