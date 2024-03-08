package com.kh.demo.domain.board.svc;

import com.kh.demo.domain.board.dao.BoardDAO;
import com.kh.demo.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoardSVCImpl implements BoardSVC{

  private BoardDAO boardDao;
  BoardSVCImpl(BoardDAO boardDAO){
    this.boardDao = boardDAO;
  }
  @Override
  public Long save(Board board) {
    return boardDao.save(board);
  }

  @Override
  public List<Board> findAll() {
    return boardDao.findAll();
  }

  @Override
  public Optional<Board> findById(Long boardId) {
    return boardDao.findById(boardId);
  }

  @Override
  public int deleteById(Long boardId) {
    return boardDao.deleteById(boardId);
  }

  @Override
  public int deleteByIds(List<Long> boardIds) {
    return boardDao.deleteByIds(boardIds);
  }

  @Override
  public int updateById(Long boardId, Board board) {
    return boardDao.updateById(boardId, board);
  }
}
