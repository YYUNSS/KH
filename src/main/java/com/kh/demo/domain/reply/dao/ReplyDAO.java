package com.kh.demo.domain.reply.dao;

import com.kh.demo.domain.entity.Reply;

import java.util.List;

public interface ReplyDAO {
  //목록
  List<Reply> findByBoardId(Long boardId ,Long reqPage, Long recCnt);
  //등록
  Long save(Reply reply);
  //삭제
  int deleteById(Long replyId);
  //수정
  int updateById(Long replyId, Reply reply);

  int totalCnt();
}
