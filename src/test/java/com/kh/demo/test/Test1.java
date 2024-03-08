package com.kh.demo.test;

import com.kh.demo.domain.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test1 {

  @Test
  @DisplayName("상품객체생성")
  void t1(){
    Product product = new Product();
    product.setProductId(1L);
    product.setPname("컴퓨터");
    product.setQuantity(10L);
    product.setPrice(1_000_000L);
    System.out.println(product);
  }
  @Test
  @DisplayName("Wrapper class")
  void t2(){
    //wrapper class
    long value1 = 5L;
    Long value2 = 10L;

    System.out.println(Long.MAX_VALUE);
    System.out.println(Long.MIN_VALUE);
    int value3 = value2.intValue();
    System.out.println("value3 = " + value3);
  }
  
  @Test
  void t3(){
    StringBuffer sql = new StringBuffer();
    sql.append("a");
    sql.append("b");
    sql.append("c");
    System.out.println("sql = " + sql);

    System.out.println("A"+"B"+"C");
  }

}
