package xyz.ibudai.translation.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.ibudai.translation.engine.EngineClient;
import xyz.ibudai.translation.engine.enums.Model;
import xyz.ibudai.translation.engine.entity.dto.RequestDTO;
import xyz.ibudai.translation.engine.entity.dto.ResponseDTO;

/**
 * (NllB)控制层
 *
 * @author ibudai
 * @since 2025-08-03 10:26:24
 */
@RestController
@RequestMapping("/api/nllb")
@RequiredArgsConstructor
public class NllbResource {

    private final EngineClient engineClient;


    @PostMapping("translate")
    public ResponseEntity<ResponseDTO> translate(@RequestBody RequestDTO reqVo) throws Exception {
        String text = reqVo.getText();
        if (StringUtils.hasLength(text)) {
            text = text.trim();
        }
        if (!StringUtils.hasLength(text)) {
            return ResponseEntity.ok(new ResponseDTO());
        }

        reqVo.setText(text);
        return ResponseEntity.ok(engineClient.translate(Model.NLLB, reqVo));
    }
}

