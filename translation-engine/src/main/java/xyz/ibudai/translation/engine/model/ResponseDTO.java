package xyz.ibudai.translation.engine.model;

import lombok.Data;
import xyz.ibudai.translation.engine.enums.Language;

@Data
public class ResponseDTO {

    private Boolean success;

    private String errorMsg;

    private Language sourceType;

    private String sourceText;

    private Language targetType;

    private String targetText;

}
