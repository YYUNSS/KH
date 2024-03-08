package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@Primary // 동일타입의 객체가 2개이상 존재할때 최우선순위로 주입받을수 있도록 설정하는 어노테이션
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO{

  private final NamedParameterJdbcTemplate template;

  //회원가입
  @Override
  public Long inserMember(Member member){
    //sql작성
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member (member_id,email,passwd,nickname) ");
    sql.append("values(member_member_id_seq.nextval, :email,:passwd,:nickname) ");
    //sql실행
    //1)sql파라미터 매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    //2)변경된 레코드 정보를 읽어오는 용도
    KeyHolder keyHolder = new GeneratedKeyHolder();
    //3)sql실행
    template.update(sql.toString(),param,keyHolder,new String[]{"member_id"});
    //4) insert된 레코드에서 회원 번호 추출
    Long product_id = ((BigDecimal)keyHolder.getKeys().get("member_id")).longValue();

    return product_id;
  }

  //이메일 존재 유무
  @Override
  public boolean existEmail(String email) {
    String sql = "select count(email) from member where email = :email ";

    Map param = Map.of("email", email);
    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt == 1 ? true : false;
  }

  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member ");
    sql.append(" where email = :email ");
    sql.append("   and passwd = :passwd ");

    Map param = Map.of("email", email, "passwd", passwd);
    try {
      Member member = template.queryForObject(sql.toString(), param, new BeanPropertyRowMapper<>(Member.class));
      return Optional.of(member);
    }catch(EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }
}
