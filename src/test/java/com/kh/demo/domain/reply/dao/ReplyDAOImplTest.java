package com.kh.demo.domain.reply.dao;

import com.kh.demo.domain.entity.Reply;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ReplyDAOImplTest {

  @Autowired
  ReplyDAO replyDAO;

  @Test
  @DisplayName("댓글 여러건 등록")
  void saveMultipleReplies() {
    long start = 1;
    long end = 115;
    for(long i=start; i<=end; i++) {
      Reply reply = new Reply();
      reply.setBoardId(3L);
      reply.setReplyContents("댓글내용"+i);
      reply.setReplyWriterEmail("user3@kh.com");
      reply.setReplyWriterId(3L);
      reply.setReplyWriterNickname("사용자3");
      Long replyId = replyDAO.save(reply);
    }
  }
  @Test
  @DisplayName("해당 게시글 댓글 목록 조회")
  void findByBoardId() {
    Long boardId = 3L;
    Long reqPage = 2L;
    Long recCnt = 10L;
    List<Reply> list = replyDAO.findByBoardId(boardId,reqPage, recCnt);
    for(Reply reply : list){
      log.info("reply={}", reply);
    }
  }

  @Test
  @DisplayName("댓글 작성")
  void save() {
    Reply reply = new Reply();
    reply.setBoardId(3L);
    reply.setReplyContents("테스트내용");
    reply.setReplyWriterId(1L);
    reply.setReplyWriterEmail("user3@kh.com");
    reply.setReplyWriterNickname("사용자3");

    Long replyId = replyDAO.save(reply);
    log.info("replyId={}", replyId);
  }



  //댓글 삭제
  @Test
  void deleteById() {
    Long rid = 2L;
    int deleteRowCnt = replyDAO.deleteById(rid);
    log.info("삭제건수={}",deleteRowCnt);
  }
  //댓글 수정
  @Test
  void updateById() {
    Long replyId = 3L;
    Reply reply = new Reply();
    reply.setReplyContents("게시글 3 수정한 내용입니다.");

    int updatedRowCnt = replyDAO.updateById(replyId, reply);
    if(updatedRowCnt == 0) {
      Assertions.fail("변경 내역이 없습니다.");
    }
  }


}