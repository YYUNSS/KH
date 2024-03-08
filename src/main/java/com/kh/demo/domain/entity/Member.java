package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
  private Long memberId;            //	NUMBER(10,0)
  private String email;             //	VARCHAR2(50 BYTE)
  private String passwd;            //	VARCHAR2(12 BYTE)
  private String nickname;          //	VARCHAR2(30 BYTE)
  private String gubun;             //	VARCHAR2(11 BYTE)
  private byte[] pic;               //	BLOB
  private LocalDateTime cdate;	    //TIMESTAMP(6)
  private LocalDateTime udate;	    //TIMESTAMP(6)
}
