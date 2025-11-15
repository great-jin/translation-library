package xyz.ibudai.translation.web.entity;

import lombok.Data;
import xyz.ibudai.translation.core.annotation.Translation;

import java.io.Serial;
import java.io.Serializable;

/**
 * (Product)实体类
 *
 * @author ibudai
 * @since 2025-08-03 10:26:24
 */
@Data
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Translation
    private String content;

}

