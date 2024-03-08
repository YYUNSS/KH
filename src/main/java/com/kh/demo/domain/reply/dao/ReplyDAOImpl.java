package com.kh.demo.domain.reply.dao;

import com.kh.demo.domain.entity.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ReplyDAOImpl implements ReplyDAO {

  private final NamedParameterJdbcTemplate template;

  ReplyDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public Long save(Reply reply) {
    return null;
  }
}
