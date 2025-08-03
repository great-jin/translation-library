package xyz.ibudai.translation.engine.translator.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import xyz.ibudai.translation.engine.model.EngineProps;
import xyz.ibudai.translation.engine.request.RequestClient;
import xyz.ibudai.translation.engine.translator.AbstractTranslator;

@Component("nllbTranslator")
public class NllbTranslator extends AbstractTranslator {

    public NllbTranslator(EngineProps engineProps,
                          ObjectMapper objectMapper,
                          RequestClient requestClient) {
        super(engineProps, objectMapper, requestClient);
    }


    @Override
    public String getApi() {
        return engineProps.getNllb();
    }

    @Override
    public String getBatchApi() {
        return engineProps.getNllbBatch();
    }
}
