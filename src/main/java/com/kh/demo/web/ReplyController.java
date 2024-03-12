package com.kh.demo.web;

import com.kh.demo.domain.entity.Reply;
import com.kh.demo.domain.reply.svc.ReplySVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.ResCode;
import com.kh.demo.web.form.member.LoginMember;
import com.kh.demo.web.form.member.SessionConst;
import com.kh.demo.web.req.reply.ReqReplySave;
import com.kh.demo.web.req.reply.ReqReplyUpdate;
import com.kh.demo.web.req.reply.ResReplySave;
import com.kh.demo.web.req.reply.ResReplyUpdate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
  private final ReplySVC replySVC;

  //목록
  @GetMapping
  public ApiResponse<?> list(
          @RequestParam("boardId") Long boarId,
          @RequestParam("reqPage") Long reqPage,
          @RequestParam("recCnt") Long recCnt
  ) {
    try {
      Thread.sleep(1000 * 1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    List<Reply> list = replySVC.findByBoardId(boarId, reqPage, recCnt);

    ApiResponse<List<Reply>> res = null;
    if (list.size() > 0) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
      res.setTotalCnt(replySVC.totalCnt());
      res.setReqPage(reqPage.intValue());
      res.setRecCnt(recCnt.intValue());
    } else {
      res = ApiResponse.createApiResponseDetail(
              ResCode.OK.getCode(), ResCode.OK.name(), "등록된 댓글 없음", list);
    }
    return res;
  }

  //등록
  @PostMapping
  public ApiResponse<?> save(
          @RequestBody ReqReplySave reqReplySave,
          HttpServletRequest request
  ) {
    log.info("reqReplySave={}", reqReplySave);

    Reply reply = new Reply();
    BeanUtils.copyProperties(reqReplySave, reply); // replyContents, boardId;

    //세션 정보로 작성자 정보 저장
    LoginMember loginMember = (LoginMember) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    reply.setReplyWriterId(loginMember.getMemberId());
    reply.setReplyWriterEmail(loginMember.getEmail());
    reply.setReplyWriterNickname(loginMember.getNickname());
    Long replyId = replySVC.save(reply);

    ResReplySave resReplySave = new ResReplySave(replyId, reqReplySave.getBoardId(), reqReplySave.getReplyContents(),reply.getReplyWriterId(),reply.getReplyWriterEmail(),reply.getReplyWriterNickname());
    String rtDetail = "댓글 등록 완료";
    ApiResponse<ResReplySave> res = ApiResponse.createApiResponseDetail(
            ResCode.OK.getCode(), ResCode.OK.name(), rtDetail, resReplySave);
    return res;
  }

  //삭제
  @DeleteMapping("/{rid}")
  public ApiResponse<?> delete(
          @PathVariable("rid") Long rid,
          HttpServletRequest request
  ) {
    LoginMember loginMember = (LoginMember) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    int deletedCnt = replySVC.deleteById(rid);
    ApiResponse<Reply> res = null;
    if (deletedCnt == 1) {
      // 작성자가 삭제 시도하려는 사람과 같은 사람인지 추가 필요


      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }
    return res;
  }

  //수정
  @PatchMapping("/{replyId}")
  public ApiResponse<?> update(
          @PathVariable("replyId") Long replyId,
          @RequestBody ReqReplyUpdate reqReplyUpdate,
          HttpServletRequest request) {
    LoginMember loginMember = (LoginMember) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);

    log.info("replyId={}", replyId);
    log.info("reqUpdate={}", reqReplyUpdate);

    Reply reply = new Reply();
    BeanUtils.copyProperties(reqReplyUpdate, reply);

    int updatedCnt = replySVC.updateById(replyId, reply);
    ApiResponse<ResReplyUpdate> res = null;
    if (updatedCnt == 1) {
      ResReplyUpdate resReplyUpdate = new ResReplyUpdate();
      BeanUtils.copyProperties(reqReplyUpdate, resReplyUpdate);
      resReplyUpdate.setReplyId(replyId);
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resReplyUpdate);

    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }
    return res;
  }
}
