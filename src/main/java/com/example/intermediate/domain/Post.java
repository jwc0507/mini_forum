package com.example.intermediate.domain;

import com.example.intermediate.controller.request.PostRequestDto;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column
  private String image;

  @Column
  private int commentCount;

  @Column
  private int likeCount;


  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;


  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostLikes> Postlikes;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  public void update(PostRequestDto postRequestDto) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
  }


  public void updateImage(String url) {
    this.image = url;
  }

  public void updateCommentCount(String upOrDown) {
    if(upOrDown.equals("up")){
      this.commentCount+=1;
    }
    else {
      this.commentCount-=1;
    }
  }

  public void updateLikeCount(String upOrDown){
    if(upOrDown.equals("up")){
      this.likeCount+=1;
    }
    else {
      this.likeCount-=1;
    }
  }



  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }

}