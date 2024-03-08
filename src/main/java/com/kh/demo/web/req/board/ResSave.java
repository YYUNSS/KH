package com.kh.demo.web.req.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResSave {
  private Long boardId;
  private String boardTitle;
}
