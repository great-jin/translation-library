package xyz.ibudai.translation.engine.entity.dto;

import lombok.Data;

@Data
public class ResponseDTO {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 失败信息
     */
    private String errorMsg;

    /**
     * 原始文本
     */
    private String sourceText;

    /**
     * 翻译结果
     */
    private String targetText;

}
