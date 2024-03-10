package com.kh.demo.domain.reply.svc;

import com.kh.demo.domain.entity.Reply;
import com.kh.demo.domain.reply.dao.ReplyDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReplySVCImpl implements ReplySVC {
  private ReplyDAO replyDAO;

  ReplySVCImpl(ReplyDAO replyDAO) {
    this.replyDAO = replyDAO;
  }

  @Override
  public List<Reply> findByBoardId(Long boardId ,Long reqPage, Long recCnt) {
    return replyDAO.findByBoardId(boardId,reqPage,recCnt);
  }

  @Override
  public Long save(Reply reply) {
    return replyDAO.save(reply);
  }

  @Override
  public int deleteById(Long replyId) {
    return replyDAO.deleteById(replyId);
  }

  @Override
  public int updateById(Long replyId, Reply reply) {
    return replyDAO.updateById(replyId,reply);
  }

  @Override
  public int totalCnt() {
    return replyDAO.totalCnt();
  }
}
