package com.itheima.jd.service;

import com.itheima.jd.po.ResultModel;

public interface ProductService {
    public ResultModel getProducts(String queryString, String catalogName, String price, String sort, Integer page) throws Exception;
}
