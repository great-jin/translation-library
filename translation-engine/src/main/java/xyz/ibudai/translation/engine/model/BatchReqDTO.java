package xyz.ibudai.translation.engine.model;

import lombok.Data;
import xyz.ibudai.translation.engine.enums.Language;

import java.util.List;

@Data
public class BatchReqDTO {

    private Language targetType;

    private List<String> textList;

}
