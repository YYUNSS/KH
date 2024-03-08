package com.kh.demo.web.form.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMember {
  private Long memberId;            //	NUMBER(10,0)
  private String email;             //	VARCHAR2(50 BYTE)
  private String nickname;          //	VARCHAR2(30 BYTE)
  private String gubun;             //	VARCHAR2(11 BYTE)

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setGubun(String gubun) {
    this.gubun = gubun;
  }
}
