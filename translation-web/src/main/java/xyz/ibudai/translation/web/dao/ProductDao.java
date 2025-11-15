package xyz.ibudai.translation.web.dao;

import xyz.ibudai.translation.web.entity.Product;

import java.util.List;

/**
 * (Product)表数据库访问层
 *
 * @author ibudai
 * @since 2025-08-03 10:26:24
 */
public interface ProductDao {

    List<Product> list();

    Product queryById(Integer id);

}

