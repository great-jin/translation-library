package xyz.ibudai.translation.engine.entity.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "engine.api")
public class ResourceProps {

    private String nllb;

    private String nllbBatch;

    private String ct2;

    private String ct2Batch;
}
