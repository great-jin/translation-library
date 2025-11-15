package xyz.ibudai.translation.web.service.impl;

import lombok.RequiredArgsConstructor;
import xyz.ibudai.translation.web.entity.Product;
import xyz.ibudai.translation.web.dao.ProductDao;
import xyz.ibudai.translation.web.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Product)表服务实现类
 *
 * @author ibudai
 * @since 2025-08-03 10:26:24
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;


    @Override
    public List<Product> list() {
        return productDao.list();
    }

    @Override
    public Product queryById(Integer id) {
        return productDao.queryById(id);
    }
}
