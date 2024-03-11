package com.kh.demo.web.req.reply;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResReplySave {
  private Long replyId;
  private Long boardId;
  private String replyContents;
  private Long replywriterId;
  private String replyWriterEmail;
  private String replyWriterNickname;
}
