package com.kh.demo.domain.product.svc;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service //SVC 역할을 하는 클래스
public class ProductSVCImpl implements ProductSVC{

  private ProductDAO productDAO;

  ProductSVCImpl(ProductDAO productDAO) {
    this.productDAO = productDAO;
  }

  @Override
  public Long save(Product product) {
    return productDAO.save(product);
  }

  @Override
  public Optional<Product> findById(Long productId) {
    return productDAO.findById(productId);
  }

  @Override
  public int deleteById(Long productId) {
    return productDAO.deleteById(productId);
  }

  @Override
  public int deleteByIds(List<Long> productIds) {
    return productDAO.deleteByIds(productIds);
  }

  @Override
  public int updateById(Long productId, Product product) {
    return productDAO.updateById(productId, product);
  }

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }

  @Override
  public List<Product> findAll(Long reqPage, Long recordCnt) {
    return productDAO.findAll(reqPage,recordCnt);
  }

  @Override
  public int totalCnt() {
    return productDAO.totalCnt();
  }
}
