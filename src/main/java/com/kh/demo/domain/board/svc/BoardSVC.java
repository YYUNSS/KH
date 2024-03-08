package com.kh.demo.domain.board.svc;

import com.kh.demo.domain.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardSVC {
  Long save(Board board);
  List<Board> findAll();
  Optional<Board> findById(Long boardId);

  int deleteById(Long boardId);

  int deleteByIds(List<Long> boardIds);

  int updateById(Long boardId, Board board);

}
