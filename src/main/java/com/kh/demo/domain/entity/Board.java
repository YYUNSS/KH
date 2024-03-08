package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
  private Long boardId;
  private String boardTitle;
  private String boardContents;
  private Long boardWriterId;
  private String boardWriterEmail;
  private String boardWriterNickname;
  private LocalDateTime cdate;
  private LocalDateTime udate;
}
//board_id                number(10),            -- 게시글 아이디
//board_title             varchar(100),          -- 제목
//baord_contents          varchar(1000),         -- 내용
//board_writer_id         number(10),            -- 작성자 ID(내부관리)
//baord_writer_email      varchar(100),          -- 작성자 이메일
//baord_writer_nickname   varchar(10),           -- 작성자 닉네임
//cdate                   timestamp,             -- 작성일자
//udate                   timestamp);            -- 수정일자
