package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reply {
  private Long replyId;  //                number(10), 댓글 아이디(내부)
  private Long boardId;  //                number(10), 원글 아이디(내부)
  private String replyContents;  //        varchar(1000), 내용
  private Long replyWriterId;  //         number(10), 작성자ID(내부)
  private String replyWriterEmail;  //    varchar(100), 작성자 이메일
  private String replyWriterNickname;  // varchar(10), 작성자 닉네임
  private LocalDateTime cdate;  //          timestamp, 작성일자
  private LocalDateTime udate;  //          timestamp  수정일자
}
