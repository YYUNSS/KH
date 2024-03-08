package com.kh.demo.web;

import com.kh.demo.domain.pubdata.NaverNews;
import com.kh.demo.domain.pubdata.StockPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/pubdata")
@RequiredArgsConstructor // final필드를 매개변수로하는 생성자를 자동 만들어줌
public class PubdataController {

  private final NaverNews naverNews;
  private final StockPrice stockPrice;

//  @Autowired
//  public PubdataController(NaverNews naverNews,StockPrice stockPrice){
//    this.naverNews = naverNews;
//    this.stockPrice = stockPrice;
//  }

  @GetMapping("/news")      // get http://localhost:9080/pubdata/news
  public String news(){
    return "pubdata/news";
  }

  @ResponseBody
  @GetMapping("/news/search")
  public String searchNews(
          @RequestParam("keyword") String keyword,
          @RequestParam("start") int start,
          @RequestParam("display") int display
  ){
    log.info("keyword={}",keyword);
    String data = naverNews.reqNews(keyword,start,display);
    return data;
  }

  @GetMapping("/stocks")
  public String stocks(){

    return "pubdata/stocks";
  }


  @ResponseBody
  @GetMapping("/stocks/search")
  public String searchStocks(
          @RequestParam("itmsNm") String itmsNm,          //종목명
          @RequestParam("beginBasDt") String beginBasDt,  //시작일
          @RequestParam("endBasDt") String endBasDt,       //종료일
          @RequestParam("numOfRows") int numOfRows,       //레코드수
          @RequestParam("pageNo") int pageNo              //요청페이지
  ){
    String data = stockPrice.reqStockPrice(itmsNm,beginBasDt,endBasDt,numOfRows,pageNo);
    log.info("itmsNm={},beginBasDt={},endBasDt={},numOfRows={},pageNo={}",itmsNm,beginBasDt,endBasDt,numOfRows,pageNo);
    log.info("data={}", data);
    return data;
  }
}
