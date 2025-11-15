package xyz.ibudai.translation.engine.entity.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "engine")
public class EngineProps {

    private String host;

    private Long connTimeout;

    private Long readTimeout;

}
