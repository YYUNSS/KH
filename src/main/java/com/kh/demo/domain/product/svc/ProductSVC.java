package com.kh.demo.domain.product.svc;

import com.kh.demo.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {
  //등록
  Long save(Product product);

  //조회
  Optional<Product> findById(Long productId);

  //단건삭제
  int deleteById(Long productId);

  //여러건삭제
  int deleteByIds(List<Long> productIds);
  
  //목록
  List<Product> findAll();
  //목록(페이징)
  List<Product> findAll(Long reqPage, Long recordCnt);

  //수정
  int updateById(Long productId, Product product);

  //총레코드 건수
  int totalCnt();
}
