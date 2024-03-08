package com.kh.demo.domain.pubdata;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class StockPriceTest {

  @Autowired
  StockPrice stockPrice;

  @Test
  void getStockPrice(){
    String itmsNm = "SK하이닉스";
    String beginBasDt = "20240201";
    String endBasDt = "20240221";
    int numOfRows = 20; // 한페이지에 보여줄 레코드수
    int pageNo = 1;     // 요청페이지
    String data = stockPrice.reqStockPrice(itmsNm,beginBasDt,endBasDt,numOfRows,pageNo);
    log.info("data={}", data);
  }
}
