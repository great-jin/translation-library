package xyz.ibudai.translation.engine.entity.dto;

import lombok.Data;
import xyz.ibudai.translation.engine.enums.Language;

import java.util.List;

@Data
public class BatchReqDTO {

    /**
     * 目标语言
     */
    private Language targetType;

    /**
     * 待翻译文本
     */
    private List<String> textList;

}
