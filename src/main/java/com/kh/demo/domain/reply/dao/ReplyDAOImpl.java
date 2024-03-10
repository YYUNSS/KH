package com.kh.demo.domain.reply.dao;

import com.kh.demo.domain.entity.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ReplyDAOImpl implements ReplyDAO {

  private final NamedParameterJdbcTemplate template;

  ReplyDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  //목록
//  @Override
//  public List<Reply> findByBoardId(Long boardId) {
//    StringBuffer sql = new StringBuffer();
//    sql.append(" select reply_writer_email, reply_writer_nickname,reply_contents ");
//    sql.append(" from reply where board_id = :boardId ");
//    MapSqlParameterSource param = new MapSqlParameterSource();
//    param.addValue("boardId",boardId);
//    List<Reply> list = template.query(sql.toString(),param, BeanPropertyRowMapper.newInstance(Reply.class));
//
//    return list;
//  }
  @Override
  public List<Reply> findByBoardId(Long boardId,Long reqPage, Long recCnt) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select reply_writer_email, reply_writer_nickname,reply_contents ");
    sql.append(" from reply ");
    sql.append(" where board_id = :boardId ");
    sql.append(" order by cdate desc ");
    sql.append(" offset (:reqPage - 1) * :recCnt rows fetch first :recCnt rows only ");

    Map<String,Long> param = Map.of("boardId",boardId,"reqPage",reqPage,"recCnt",recCnt);
    List<Reply> list = template.query(sql.toString(),param, BeanPropertyRowMapper.newInstance(Reply.class));

    return list;
  }

  //등록
  @Override
  public Long save(Reply reply) {
    StringBuffer sql = new StringBuffer();
    sql.append(" insert into reply(reply_id,board_id,reply_contents,reply_writer_id, reply_writer_email,reply_writer_nickname) ");
    sql.append(" values(reply_reply_id_seq.nextval, :boardId, :replyContents, :replyWriterId , :replyWriterEmail, :replyWriterNickname) ");
    SqlParameterSource param = new BeanPropertySqlParameterSource(reply);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"reply_id","board_id","reply_contents","reply_writer_id", "reply_writer_email","reply_writer_nickname"});
    Long reply_id = ((BigDecimal)keyHolder.getKeys().get("reply_id")).longValue();

    return reply_id;
  }
  //삭제
  @Override
  public int deleteById(Long replyId) {
    StringBuffer sql = new StringBuffer();
    sql.append(" delete from reply ");
    sql.append(" where reply_id = :replyId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("replyId", replyId);

    int deletedRowCnt = template.update(sql.toString(), param);

    return deletedRowCnt;
  }

  //수정
  @Override
  public int updateById(Long replyId, Reply reply) {
    StringBuffer sql = new StringBuffer();

    sql.append(" update reply " );
    sql.append(" set reply_contents = :replyContents, ");
    sql.append(" udate = default " );
    sql.append(" where reply_id = :replyId" );

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("replyId", replyId)
            .addValue("replyContents", reply.getReplyContents());

    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }
  //총 레코드 건수
  @Override
  public int totalCnt() {
    String sql = "SELECT COUNT(reply_id) FROM reply ";
    SqlParameterSource param = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt;
  }
}
