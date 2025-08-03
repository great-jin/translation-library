package xyz.ibudai.translation.engine.model;

import lombok.Data;

import java.util.List;

@Data
public class BatchReqDTO {

    private List<RequestDTO> requests;

}
