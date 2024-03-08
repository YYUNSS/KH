package com.kh.demo.domain.board.dao;

import com.kh.demo.domain.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardDAO {
  Long save(Board board);
  List<Board> findAll();

  Optional<Board> findById(Long boardId);

  int deleteById(Long boardId);

  //  여러건 삭제
  int deleteByIds(List<Long> boardIds);

  int updateById(Long boardId, Board board);
}