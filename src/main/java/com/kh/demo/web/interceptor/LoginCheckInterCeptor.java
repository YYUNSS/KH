package com.kh.demo.web.interceptor;

import com.kh.demo.web.form.member.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;

@Slf4j
public class LoginCheckInterCeptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //리다이렉트 URL만들기
    String redirectUrl = null;

    String requestURI = request.getRequestURI();
    log.info("요청uri={}",requestURI);              // /products/news
    log.info("요청url={}",request.getRequestURL()); // http://localhost:9080/products/news

    //요청 파리미터 정보가 있는 경우 http://localhost:9080/products/news?a=1&b=2
    if(request.getQueryString() != null){
      log.info("요청url getQueryString={}",request.getQueryString());
      //요청파리미터 인코딩
      String queryString = URLEncoder.encode(request.getQueryString(),"UTF-8");   //a=1&b=2
      StringBuffer str = new StringBuffer();
      redirectUrl = str.append(requestURI).append("?").append(queryString).toString(); //products?a=1&b=2
      log.info("redirectUrl={}",redirectUrl);
    }else{
    //요청 파리미터 정보가 없는 경우 http://localhost:9080/products/news
      redirectUrl = requestURI;    // /products/news
    }

    //세션 정보 읽어오기
    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
      log.info("미인증상태");
      // 302 http://localhost:9080/login
      response.sendRedirect("/login?redirectUrl="+redirectUrl);
      return false;   //다음 인터셉터 포함하여 컨트롤러 수행하지 않음
    }
    return true;
  }
}
