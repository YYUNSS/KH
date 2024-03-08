package com.kh.demo.domain.pubdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class NaverNews {

  private final WebClient webClient;
  private String baseUrl = "https://openapi.naver.com";
  //개인 id정보로 수정!!
  private final String Client_Id = "rU1BGqPDYCKpVAtW30FM";
  private final String Client_Secret = "cOAsPt0pi2";

  @Autowired
  public NaverNews(WebClient.Builder webClientBilder){

//    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
//    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

    this.webClient = webClientBilder
//            .uriBuilderFactory(factory)
            .baseUrl(baseUrl)
//            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE) //json포맷요청
            .build();
  }

  public String reqNews(String keyword,int start, int display){
    final String query = keyword; //"LG화학";
 //   final int display = 10;
//    final int start = 1;

    // http get 요청하면 http 응답메시지 수신
    Mono<String> response = webClient.get()
            .uri(uriBuilder -> uriBuilder
              .path("/v1/search/news.json")                 //베이스url 이하 경로
              .queryParam("query",query)    //query
              .queryParam("display",display)       //display
              .queryParam("start",start)          //start
//              .queryParam("sort","")                       //sort
              .build())
            .header("X-Naver-Client-Id",Client_Id)
            .header("X-Naver-Client-Secret",Client_Secret)
            .retrieve()
            .bodyToMono(String.class);
    // http응답메시지 바디를 읽어 문자열로 반환 
    String data = response.block();
    return data;
  }
}
