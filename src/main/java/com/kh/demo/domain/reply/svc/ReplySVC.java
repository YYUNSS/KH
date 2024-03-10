package com.kh.demo.domain.reply.svc;

import com.kh.demo.domain.entity.Reply;

import java.util.List;

public interface ReplySVC {
  //목록
  List<Reply> findByBoardId(Long boardId ,Long reqPage, Long recCnt);
  //등록
  Long save(Reply reply);
  //삭제
  int deleteById(Long replyId);
  //수정
  int updateById(Long replyId, Reply reply);

  //총 레코드수
  int totalCnt();
}
