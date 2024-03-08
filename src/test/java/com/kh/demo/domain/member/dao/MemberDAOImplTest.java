package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
public class MemberDAOImplTest {

  @Autowired
  MemberDAO memberDAO;

  @Test
  void test(){
    log.info("memberDAO={}", memberDAO.getClass().getName());
  }

  @Test
  @DisplayName("회원가입")
  @Transactional
  void inserMember(){
    Member member = new Member();
    member.setEmail("user3@kh.com");
    member.setPasswd("user3");
    member.setNickname("사용자3");
    Long memberId = memberDAO.inserMember(member);
    log.info("memberId={}", memberId);
  }

  @Test
  @DisplayName("이메일(O)")
  void existMemberId(){
    boolean exit = memberDAO.existEmail("user1@kh.com");
    Assertions.assertThat(exit).isEqualTo(true);
  }
  @Test
  @DisplayName("이메일(X)")
  void dontExistMemberId(){
    boolean exit = memberDAO.existEmail("zzzz@kh.com");
    Assertions.assertThat(exit).isEqualTo(false);
  }
  @Test
  @DisplayName("회원조회(O)")
  void findByEmailAndPasswd(){
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("user1@kh.com", "user1");
    // 결과 검증
    Assertions.assertThat(optionalMember).isPresent(); // Optional이 존재해야 함

    Member findedMember = optionalMember.get();
    Assertions.assertThat(findedMember.getEmail()).isEqualTo("user1@kh.com"); // 이메일 일치 여부 확인
    Assertions.assertThat(findedMember.getPasswd()).isEqualTo("user1"); // 비밀번호 일치 여부 확인
  }
  @Test
  @DisplayName("회원조회(X)")
  void findByEmailAndPasswd2(){
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("user9@kh.com", "user1");
    // 결과 검증
    Assertions.assertThat(optionalMember).isEmpty(); // Optional이 없어 함
  }
}
