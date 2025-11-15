package xyz.ibudai.translation.engine.translator.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import xyz.ibudai.translation.engine.entity.props.ResourceProps;
import xyz.ibudai.translation.engine.request.RequestClient;
import xyz.ibudai.translation.engine.translator.AbstractTranslator;

@Component("nllbTranslator")
public class NllbTranslator extends AbstractTranslator {

    public NllbTranslator(ResourceProps resourceProps,
                          ObjectMapper objectMapper,
                          RequestClient requestClient) {
        super(resourceProps, objectMapper, requestClient);
    }


    @Override
    public String getApi() {
        return resourceProps.getCt2();
    }

    @Override
    public String getBatchApi() {
        return resourceProps.getCt2Batch();
    }
}
