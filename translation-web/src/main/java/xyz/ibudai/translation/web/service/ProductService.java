package xyz.ibudai.translation.web.service;

import xyz.ibudai.translation.web.entity.Product;

import java.util.List;

/**
 * (Product)表服务接口
 *
 * @author ibudai
 * @since 2025-08-03 10:26:24
 */
public interface ProductService {

    List<Product> list();

    Product queryById(Integer id);

}
