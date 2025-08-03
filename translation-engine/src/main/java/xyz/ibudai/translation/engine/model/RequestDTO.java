package xyz.ibudai.translation.engine.model;

import lombok.Data;
import xyz.ibudai.translation.engine.enums.Language;

@Data
public class RequestDTO {

    private String text;

    private Language targetType;

}
