package com.kh.demo.web.req.board;

import lombok.Data;

//게시글 등록
@Data
public class ReqSave {
  private String boardTitle;
  private String boardContents;
}
