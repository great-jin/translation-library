package xyz.ibudai.translation.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "xyz.ibudai.translation.engine",
        "xyz.ibudai.translation.core",
        "xyz.ibudai.translation.web"
})
@MapperScan("xyz.ibudai.translation.web.dao")
public class TranslationEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranslationEngineApplication.class, args);
    }

}
