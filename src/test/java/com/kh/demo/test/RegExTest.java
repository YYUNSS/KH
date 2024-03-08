package com.kh.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

@Slf4j
public class RegExTest {

  @Test
  @DisplayName("숫자3자리이상10자리이하")
  void t1(){
    String str = "12";
    String pattern = "[0-9]{3,10}";

//    if(Pattern.matches(pattern,str)){
    if(Pattern.matches("\\d{3,10}","1232")){
      log.info("패턴일치");
    }else{
      log.info("패턴불일치");
    }
  }
  @Test
  @DisplayName("전화번호패턴체크")
  void t2(){
    if(Pattern.matches("\\d{3}-\\d{4}-\\d{4}","01012345678")){
      log.info("패턴일치");
    }else{
      log.info("패턴불일치");
    }
  }
  @Test
  @DisplayName("상품명패턴체크")
  void t3(){
    String input = "aB한123_-"; // 검사할 문자열 입력

    // 정규표현식 패턴
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";

    // 패턴과 입력 문자열을 비교
    if (Pattern.matches(pattern, input)) {
      log.info("유효한 패턴입니다.");
    } else {
      log.info("패턴이 유효하지 않습니다.");
    }
  }
  @Test
  @DisplayName("이메일패턴 체크")
  void t4(){
    String email = "example@example.com"; // 검사할 이메일 주소 입력

    // 정규표현식 패턴
    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // 패턴과 입력 이메일 주소를 비교
    if (Pattern.matches(emailPattern, email)) {
      log.info("유효한 이메일 주소입니다.");
    } else {
      log.info("이메일 주소가 유효하지 않습니다.");
    }
  }
}
