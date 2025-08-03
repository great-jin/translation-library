package xyz.ibudai.translation.core.intercept;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xyz.ibudai.translation.core.annotation.Translation;
import xyz.ibudai.translation.engine.EngineClient;
import xyz.ibudai.translation.engine.enums.Language;
import xyz.ibudai.translation.engine.enums.Model;
import xyz.ibudai.translation.engine.model.RequestDTO;
import xyz.ibudai.translation.engine.model.ResponseDTO;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Component
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {
                        MappedStatement.class,
                        Object.class,
                        RowBounds.class,
                        ResultHandler.class
                }
        )
)
@RequiredArgsConstructor
public class TranslationIntercept implements Interceptor {

    private final EngineClient engineClient;


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        SqlCommandType sqlType = ms.getSqlCommandType();
        if (sqlType != SqlCommandType.SELECT) {
            // 非查询不处理
            return invocation.proceed();
        }

        Object params = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];

        Executor executor = (Executor) invocation.getTarget();
        BoundSql boundSql = ms.getBoundSql(params);
        CacheKey cacheKey = executor.createCacheKey(ms, params, rowBounds, boundSql);

        List<Object> dataList = executor.query(
                ms,
                params,
                rowBounds,
                resultHandler,
                cacheKey,
                boundSql
        );
        // 字段翻译
        fieldTranslate(dataList);
        return dataList;
    }


    private void fieldTranslate(List<Object> dataList) throws Exception {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        Locale locale = LocaleContextHolder.getLocale();
        Language language = Language.valueOf(locale.getLanguage());
        for (Object row : dataList) {
            Field[] fields = row.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType() != String.class) {
                    // 仅处理文本
                    continue;
                }
                if (!field.isAnnotationPresent(Translation.class)) {
                    // 不存在注解
                    continue;
                }
                Object fieldContent = field.get(row);
                if (Objects.isNull(fieldContent) || fieldContent.toString().isEmpty()) {
                    // 内容为空
                    continue;
                }

                RequestDTO req = new RequestDTO();
                req.setText(fieldContent.toString());
                req.setTargetType(language);
                ResponseDTO res = engineClient.translate(Model.NLLB, req);
                field.set(row, res.getTargetText());
            }
        }
    }
}
