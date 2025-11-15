package xyz.ibudai.translation.core.runner;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xyz.ibudai.translation.core.intercept.TranslationIntercept;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SqlFactoryRunner implements ApplicationRunner {

    @Value("${engine.switch.intercept:true}")
    private Boolean enableIntercept;


    private final TranslationIntercept translationIntercept;

    private final List<SqlSessionFactory> sqlSessionFactoryList;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (Boolean.FALSE.equals(enableIntercept)) {
            return;
        }

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            // Inject interceptor to sql factory
            sqlSessionFactory.getConfiguration().addInterceptor(translationIntercept);
        }
    }
}