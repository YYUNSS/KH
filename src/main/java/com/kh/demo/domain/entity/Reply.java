package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reply {
  private Long reply_id;  //                number(10), 댓글 아이디(내부)
  private Long board_id;  //                number(10), 원글 아이디(내부)
  private String reply_contents;  //        varchar(1000), 내용
  private Long reply_writer_id;  //         number(10), 작성자ID(내부)
  private String reply_writer_email;  //    varchar(100), 작성자 이메일
  private String reply_writer_nickname;  // varchar(10), 작성자 닉네임
  private LocalDateTime cdate;  //          timestamp, 작성일자
  private LocalDateTime udate;  //          timestamp  수정일자
}
