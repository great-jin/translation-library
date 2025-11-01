package xyz.ibudai.translation.engine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.ibudai.translation.engine.enums.Model;
import xyz.ibudai.translation.engine.model.BatchReqDTO;
import xyz.ibudai.translation.engine.model.RequestDTO;
import xyz.ibudai.translation.engine.model.ResponseDTO;
import xyz.ibudai.translation.engine.translator.AbstractTranslator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class EngineClient {

    private final Map<String, AbstractTranslator> translators;


    /**
     * Translate response dto.
     *
     * @param model  the model
     * @param reqDto the req dto
     * @return the response dto
     * @throws Exception the exception
     */
    public ResponseDTO translate(Model model, RequestDTO reqDto) throws Exception {
        if (Objects.isNull(reqDto)) {
            return null;
        }

        // 请求服务
        return getTranslator(model).translate(reqDto);
    }

    /**
     * Batch translate response dto.
     *
     * @param model  the model
     * @param reqDto the req dto
     * @return the response dto
     * @throws Exception the exception
     */
    public List<ResponseDTO> batchTranslate(Model model, BatchReqDTO reqDto) throws Exception {
        if (Objects.isNull(reqDto)) {
            return null;
        }

        // 请求批量服务
        return getTranslator(model).batchTranslate(reqDto);
    }


    protected AbstractTranslator getTranslator(Model model) {
        AbstractTranslator translator = translators.get(model.getComponents());
        if (Objects.isNull(translator)) {
            throw new IllegalArgumentException("No translator found for " + model);
        }
        return translator;
    }
}
