package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.form.product.AddForm;
import com.kh.demo.web.form.product.UpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller // Controller 역할을 하는 클래스
@RequestMapping("/products")    // http://localhost:9080/products
public class ProductControllerV2 {

  private ProductSVC productSVC;
  ProductControllerV2(ProductSVC productSVC){
    this.productSVC = productSVC;
  }

  //상품등록양식
  @GetMapping("/add")         // Get, http://localhost:9080/products/add
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "productv2/add";     // view이름  상품등록화면
  }

  //상품등록처리
  @PostMapping("/add")        // Post, http://localhost:9080/products/add
  public String add(
          AddForm addForm,    //form객체 : 양식과 매핑되는 객체
          Model model,
          RedirectAttributes redirectAttributes
          ){

    log.info("addForm={}", addForm);
    //유효성체크
    //필드 레벨
    //1-1)상품명
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";
    if (!Pattern.matches(pattern, addForm.getPname())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_pname","영문/숫자/한글/_-가능, 3~10자리");
      return "productv2/add";
    }
    //1-2)수량
    pattern = "^\\d{1,10}$";
    if (!Pattern.matches(pattern, String.valueOf(addForm.getQuantity()))) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_quantity","숫자0~9사이 입력가능 1~10자리");
      return "productv2/add";
    }
    //1-3)가격
    pattern = "^\\d{1,10}$";
    if (!Pattern.matches(pattern, String.valueOf(addForm.getPrice()))) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_price","숫자0~9사이 입력가능 1~10자리");
      return "productv2/add";
    }
    //글로벌 레벨
    if(addForm.getQuantity() * addForm.getPrice() > 10_000_000){
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_global","총액 1000만원 초과합니다");
      return "productv2/add";
    }

    //상품등록
    Product product = new Product();
    product.setPname(addForm.getPname());
    product.setQuantity(addForm.getQuantity());
    product.setPrice(addForm.getPrice());

    Long productId = productSVC.save(product);
    log.info("상품번호={}", productId);

    redirectAttributes.addAttribute("pid",productId);
    return "redirect:/products/{pid}/detail"; // 상품조회화면  302 GET http://서버:9080/products/상품번호/detail
  }

  //조회
  @GetMapping("/{pid}/detail")       //GET http://localhost:9080/products/상품번호/detail
  public String findById(@PathVariable("pid") Long productId, Model model){

    Optional<Product> findedProduct = productSVC.findById(productId);
    Product product = findedProduct.orElseThrow();
    model.addAttribute("product", product);

    return "productv2/detailForm";
  }

  //단건삭제
  @ResponseBody   //응답 메세지 바디에 직접 작성
  @DeleteMapping("/{pid}/del")
  public ResponseEntity<?> deleteById(@PathVariable("pid") Long productId){
    log.info("deleteById={}",productId);

    //1)상품 삭제 -> 상품테이블에서 삭제
    int deletedRowCnt = productSVC.deleteById(productId);
    if(deletedRowCnt == 1){
      return ResponseEntity.ok().build(); //응답코드 200 OK
    }else{
      return ResponseEntity.notFound().build(); // 응답코드 404 NotFound
    }
    //return "redirect:/products";     // GET http://localhost:9080/products/
  }

  //여러건삭제
  @PostMapping("/del")          // POST http://localhost:9080/products/del
  public String deleteByIds(@RequestParam("pids") List<Long> pids) {

    log.info("deleteByIds={}",pids);
    int deletedRowCnt = productSVC.deleteByIds(pids);

    return "redirect:/products";    // GET http://localhost:9080/products/
  }

  //수정양식
  @GetMapping("/{pid}/edit")      // GET http://locahost:9080/products/상품번호/edit
  public String updateForm(
          @PathVariable("pid") Long productId,
          Model model){

    Optional<Product> optionalProduct = productSVC.findById(productId);
    Product findedProduct = optionalProduct.orElseThrow();

    model.addAttribute("product",findedProduct);
    return "productv2/updateForm";
  }
  //수정 처리
  @PostMapping("/{pid}/edit")
  public String update(
          //경로변수 pid로부터 상품번호을 읽어온다
          @PathVariable("pid") Long productId,
          //요청메세지 바디로부터 대응되는 상품정보를 읽어온다.
          UpdateForm updateForm,
          //리다이렉트시 경로변수에 값을 설정하기위해 사용
          RedirectAttributes redirectAttributes,
          Model model){

    //유효성체크
    //필드 레벨
    //1-1)상품명
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";
    if (!Pattern.matches(pattern, updateForm.getPname())) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_pname","영문/숫자/한글/_-가능, 3~10자리");
      return "productv2/updateForm";
    }
    //1-2)수량
    pattern = "^\\d{1,10}$";
    if (!Pattern.matches(pattern, String.valueOf(updateForm.getQuantity()))) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_quantity","숫자0~9사이 입력가능 1~10자리");
      return "productv2/updateForm";
    }
    //1-3)가격
    pattern = "^\\d{1,10}$";
    if (!Pattern.matches(pattern, String.valueOf(updateForm.getPrice()))) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_price","숫자0~9사이 입력가능 1~10자리");
      return "productv2/updateForm";
    }
    //글로벌 레벨
    if(updateForm.getQuantity() * updateForm.getPrice() > 10_000_000){
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_global","총액 1000만원 초과합니다");
      return "productv2/updateForm";
    }

    //정상처리
    Product product = new Product();
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());
    int updateRowCnt = productSVC.updateById(productId, product);

    redirectAttributes.addAttribute("pid",productId);
    return "redirect:/products/{pid}/detail";
  }
  
//  //목록
//  @GetMapping   // GET http://localhost:9080/products
//  public String findAll(Model model){
//
//    List<Product> list = productSVC.findAll();
//    model.addAttribute("list", list);
//
//    return "productv2/all";
//  }
  //목록(페이징)
  @GetMapping   // GET http://localhost:9080/products?reqPage=2&reqCnt=10
  public String findAllByPaging(
          Model model,
          @RequestParam(value = "reqPage", defaultValue = "1") Long reqPage, // 요청 페이지
          @RequestParam(value = "reqCnt", defaultValue = "10") Long reqCnt,   // 레코드 수
          @RequestParam(value = "cpgs", defaultValue = "1") Long cpgs,   //페이지 그룹 시작번호
          @RequestParam(value = "cp", defaultValue = "1") Long cp   // 현재 페이지
  ){

    List<Product> list = productSVC.findAll(reqPage, reqCnt);
    int totalCnt = productSVC.totalCnt();

    model.addAttribute("list", list);
    model.addAttribute("totalCnt", totalCnt);
    model.addAttribute("cpgs", cpgs);
    model.addAttribute("cp", cp);

    return "productv2/allByPaging";
  }
}
