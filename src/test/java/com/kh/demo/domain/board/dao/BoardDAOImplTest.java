package com.kh.demo.domain.board.dao;

import com.kh.demo.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BoardDAOImplTest {
  @Autowired
  BoardDAO boardDAO;
  @Test
  void findAll() {
  }

  @Test
  void findById() {
  }

  @Test
  void save() {
  }

  @Test
  void deleteById() {
  }

  @Test
  void deleteByIds() {
  }

  @Test
  @DisplayName("수정")
  void updateById() {
    Long boardId = 1L;
    Board board = new Board();
    board.setBoardTitle("가나다");
    board.setBoardContents("내용");
  }

  @Test
  void testSave() {
    Board board = new Board();
    board.setBoardTitle("제목");
    board.setBoardContents("내용");
    board.setBoardWriterNickname("작성자");

    Long boardId = boardDAO.save(board);
  }
}
