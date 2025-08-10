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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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


    private void fieldTranslate(List<Object> dataList) throws Throwable {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        // 获取语言环境
        Locale locale = LocaleContextHolder.getLocale();
        Language language = Language.valueOf(locale.getLanguage());

        // 反射实例
        Class<?> clazz = dataList.get(0).getClass();
        MethodHandles.Lookup lookup =
                MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());

        // 数据处理
        Map<Field, MethodHandle> getterHandleMap = new HashMap<>();
        Map<Field, MethodHandle> setterHandleMap = new HashMap<>();
        for (Object row : dataList) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() != String.class || !field.isAnnotationPresent(Translation.class)) {
                    // 非字符串或不存在注解
                    continue;
                }

                try {
                    // 获取目标字段
                    MethodHandle getter = findGetter(lookup, getterHandleMap, clazz, field);
                    String fieldContent = (String) getter.invoke(row);
                    if (fieldContent == null || fieldContent.isEmpty()) {
                        // 内容为空不处理
                        continue;
                    }

                    // 翻译请求
                    RequestDTO req = new RequestDTO();
                    req.setText(fieldContent);
                    req.setTargetType(language);
                    ResponseDTO res = engineClient.translate(Model.NLLB, req);
                    if (!Boolean.TRUE.equals(res.getSuccess())) {
                        log.error("request failed, res: {}", res);
                        continue;
                    }

                    // 设置翻译结果
                    MethodHandle setter = findSetter(lookup, setterHandleMap, clazz, field);
                    setter.invoke(row, res.getTargetText());
                } catch (Throwable e) {
                    // 失败跳过，保留原值
                    log.error("translate error", e);
                }
            }
        }
        getterHandleMap.clear();
        setterHandleMap.clear();
    }

    private MethodHandle findGetter(MethodHandles.Lookup lookup, Map<Field, MethodHandle> map, Class<?> clazz, Field field) {
        return map.computeIfAbsent(
                field, k -> {
                    try {
                        return lookup.findGetter(clazz, k.getName(), String.class);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
        );
    }

    private MethodHandle findSetter(MethodHandles.Lookup lookup, Map<Field, MethodHandle> map, Class<?> clazz, Field field) {
        return map.computeIfAbsent(
                field, k -> {
                    try {
                        return lookup.findSetter(clazz, k.getName(), String.class);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
        );
    }
}
