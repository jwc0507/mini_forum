package com.example.intermediate.domain;

import com.example.intermediate.controller.request.CommentRequestDto;

import javax.persistence.*;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "post_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @Column(nullable = false)
  private String content;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentLikes> Commentlikes;

  @Column
  private int countCommentLikes;

  public void update(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public void updateLikeCount(String upOrDown){
    System.out.println(upOrDown);
    System.out.println("변경전 : "+this.countCommentLikes);
    if(upOrDown.equals("up")){
      this.countCommentLikes+=1;
    }
    else {
      this.countCommentLikes-=1;
    }
    System.out.println("변경후 : "+this.countCommentLikes);
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }


}
