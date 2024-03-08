package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MemberSVCImplTest {

  @Autowired
  MemberSVC memberSVC;

  @Test
  @DisplayName("회원가입")
  @Transactional
    //테스트환경 : 실행후 rollback
  void joinMember(){
    Member member = new Member();
    member.setEmail("user4@kh.com");
    member.setPasswd("user4");
    member.setNickname("사용자4");
    Long memberId = memberSVC.joinMember(member);
    log.info("memberId={}", memberId);
  }
}