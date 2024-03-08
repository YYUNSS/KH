package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.form.member.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;

  //회원가입양식
  @GetMapping("/join")
  public String joinForm(){
    return "member/joinForm";
  }

  //회원가입처리
  @PostMapping("/join")
  public String join(JoinForm joinForm){
    log.info("joinForm={}", joinForm);
    //1)유효성 검증
    
    //2)가입처리
    Member member = new Member();
    BeanUtils.copyProperties(joinForm,member);
    Long memberId = memberSVC.joinMember(member);

    return (memberId !=null ) ? "redirect:/login" : "redirect:/join";
  }

}
