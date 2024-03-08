package com.kh.demo.web.req.product;

import lombok.Data;

@Data
public class ReqSave {
  private String pname;
  private Long quantity;
  private Long price;
}
