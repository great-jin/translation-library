package xyz.ibudai.translation.engine.model;

import lombok.Data;

@Data
public class ResponseDTO {

    private Boolean success;

    private String errorMsg;

    private String sourceText;

    private String targetText;

}
