package com.kh.demo.web.req.reply;

import lombok.Data;

@Data
public class ReqReplySave {
  private String replyContents;
  private Long boardId;
}
