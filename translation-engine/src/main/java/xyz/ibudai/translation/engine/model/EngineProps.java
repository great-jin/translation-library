package xyz.ibudai.translation.engine.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "engine")
public class EngineProps {

    private String host;

    private String nllb;

    private String nllbBatch;

    private Long connTimeout;

    private Long readTimeout;

}
