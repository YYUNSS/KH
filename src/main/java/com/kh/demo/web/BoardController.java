package com.kh.demo.web;

import com.kh.demo.domain.board.svc.BoardSVC;
import com.kh.demo.domain.entity.Board;
import com.kh.demo.web.form.board.AddForm;
import com.kh.demo.web.form.board.UpdateForm;
import com.kh.demo.web.form.member.LoginMember;
import com.kh.demo.web.form.member.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/boardlist")
public class BoardController {
  private BoardSVC boardSVC;

  BoardController(BoardSVC boardSVC) {
    this.boardSVC = boardSVC;
  }

  @GetMapping
  public String findAll(Model model) {
    List<Board> list = boardSVC.findAll();
    model.addAttribute("list", list);

    return "boardV2/all";
  }
  //게시물 등록 양식
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "boardV2/add";
  }
  //게시물 등록 처리
  @PostMapping("/add")  // Get, http://localhost:9080//Boardlist/add
  public String add(
          AddForm addForm,
          Model model,
          RedirectAttributes redirectAttributes,
          HttpServletRequest request
  ) {
    //작성자 확인
    LoginMember loginMember = (LoginMember) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    model.addAttribute("board", addForm);

    //게시글 등록
    Board board = new Board();
    board.setBoardTitle(addForm.getBoardTitle());
    board.setBoardContents(addForm.getBoardContents());
    board.setBoardWriterId(loginMember.getMemberId());
    board.setBoardWriterEmail(loginMember.getEmail());
    board.setBoardWriterNickname(loginMember.getNickname());


    Long boardId = boardSVC.save(board);
    log.info("boardId={}", boardId);

    redirectAttributes.addAttribute("bid", boardId);
    return "redirect:/boardlist/{bid}/detail";
  }


  //조회
  @GetMapping("/{bid}/detail")
  public String findById(@PathVariable("bid") Long boardId, Model model) {
    Optional<Board> findedBoard = boardSVC.findById(boardId);
    Board board = findedBoard.orElseThrow();
    model.addAttribute("board", board);
    return "boardV2/viewBoard";
  }

  //단건 삭제
  @GetMapping("/{bid}/del")
  public String deleteById(@PathVariable("bid") Long boardId) {
    log.info("deleteById={}", boardId);

    int deletedRowCnt = boardSVC.deleteById(boardId);
    return "redirect:/boardlist"; // GET http://localhost:9080/boardlist/
  }


  //여러건 삭제
  @PostMapping("/del")
  public String deleteByIds(@RequestParam("bids") List<Long> bids) {
    log.info("deleteByIds={}", bids);
    int deletedRowCnt = boardSVC.deleteByIds(bids);
    return "redirect:/boardlist";
  }

 // 수정 양식
  @GetMapping("/{bid}/edit")
  public String updateForm(
          @PathVariable("bid") Long boardId,
          Model model){
    Optional<Board> optionalBoard = boardSVC.findById(boardId);
    Board findedboard = optionalBoard.orElseThrow();
    model.addAttribute("board", findedboard);
    return "boardV2/updateForm";
  }
  //수정 처리
  @PostMapping("{bid}/edit")
  public String update(@PathVariable("bid") Long boardId,
                       UpdateForm updateForm,
                       RedirectAttributes redirectAttributes,
                       Model model) {
    //유효성 검사
    //필드레벨
    //1-1) 게시글 제목
    String pattern = "^[a-zA-Z0-9가-힣\\W_-]{1,10}$";
    if (!Pattern.matches(pattern, updateForm.getBoardTitle())) {
      model.addAttribute("board", updateForm);
      model.addAttribute("err_title", "1~10자 입력가능");
      return "boardV2/updateForm";
    }
    //1-2) 게시글 내용
    pattern = "^[a-zA-Z0-9가-힣\\W_-]{1,100}$";
    if (!Pattern.matches(pattern, updateForm.getBoardContents())) {
      model.addAttribute("board", updateForm);
      model.addAttribute("err_contents", "1~100자 입력가능");
      return "boardV2/updateForm";
    }

    //정상처리
    Board board = new Board();
    board.setBoardTitle(updateForm.getBoardTitle());
    board.setBoardContents(updateForm.getBoardContents());

    int updateRowCnt = boardSVC.updateById(boardId, board);
    redirectAttributes.addAttribute("bid", boardId);
    return "redirect:/boardlist/{bid}/detail";
  }
}
