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
import org.springframework.stereotype.Component;
import xyz.ibudai.translation.core.TranslateManager;
import java.util.List;

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

    private final TranslateManager translateManager;


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
        return translateManager.fieldTranslate(dataList);
    }
}
