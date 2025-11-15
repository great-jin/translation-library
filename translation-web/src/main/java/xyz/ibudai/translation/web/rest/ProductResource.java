package xyz.ibudai.translation.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import xyz.ibudai.translation.web.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ibudai.translation.web.service.ProductService;

import java.util.List;
import java.util.Locale;

/**
 * (Product)表控制层
 *
 * @author ibudai
 * @since 2025-08-03 10:26:24
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;


    @GetMapping("list")
    public ResponseEntity<List<Product>> list(@RequestParam("language") String language) {
        Locale locale = Locale.forLanguageTag(language);
        LocaleContextHolder.setLocale(locale);

        return ResponseEntity.ok(productService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productService.queryById(id));
    }
}

