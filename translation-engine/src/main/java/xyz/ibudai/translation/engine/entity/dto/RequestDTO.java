package xyz.ibudai.translation.engine.entity.dto;

import lombok.Data;
import xyz.ibudai.translation.engine.enums.Language;

@Data
public class RequestDTO {

    /**
     * 翻译质量
     */
    private Integer quality;

    /**
     * 待翻译文本
     */
    private String text;

    /**
     * 目标语言
     */
    private Language targetType;

}
