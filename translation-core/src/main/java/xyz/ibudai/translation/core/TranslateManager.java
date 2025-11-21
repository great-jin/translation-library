package xyz.ibudai.translation.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xyz.ibudai.translation.core.annotation.Translation;
import xyz.ibudai.translation.engine.EngineClient;
import xyz.ibudai.translation.engine.entity.dto.BatchReqDTO;
import xyz.ibudai.translation.engine.entity.dto.ResponseDTO;
import xyz.ibudai.translation.engine.enums.Language;
import xyz.ibudai.translation.engine.enums.Model;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TranslateManager {

    private final EngineClient engineClient;


    public List<Object> fieldTranslate(List<Object> dataList) throws Throwable {
        if (CollectionUtils.isEmpty(dataList)) {
            return Collections.emptyList();
        }
        // 获取语言环境
        Locale locale = LocaleContextHolder.getLocale();
        Language language = Language.valueOf(locale.getLanguage());
        if (Objects.equals(language, Language.zh)) {
            return dataList;
        }

        // 反射实例
        Class<?> clazz = dataList.get(0).getClass();
        MethodHandles.Lookup lookup =
                MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());

        // 原始记录读取
        Map<Field, MethodHandle> getterHandleMap = new HashMap<>();
        Map<Field, MethodHandle> setterHandleMap = new HashMap<>();
        List<String> sentences = readContent(dataList, clazz, lookup, getterHandleMap);

        // 翻译服务
        BatchReqDTO reqDTO = new BatchReqDTO();
        reqDTO.setTargetType(language);
        reqDTO.setTextList(sentences);
        List<ResponseDTO> resList = engineClient.batchTranslate(Model.NLLB, reqDTO);
        Map<String, String> transMap = new HashMap<>();
        for (ResponseDTO responseDTO : resList) {
            transMap.put(responseDTO.getSourceText(), responseDTO.getTargetText());
        }

        // 内容回填
        this.writeContent(dataList, clazz, transMap, lookup, getterHandleMap, setterHandleMap);

        // 结果返回
        return dataList;
    }


    private List<String> readContent(List<Object> dataList,
                                     Class<?> clazz,
                                     MethodHandles.Lookup lookup,
                                     Map<Field, MethodHandle> getterHandleMap) {
        List<String> sentences = new ArrayList<>();
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

                    sentences.add(fieldContent);
                } catch (Throwable e) {
                    log.error("readContent error", e);
                }
            }
        }
        return sentences;
    }

    private void writeContent(List<Object> dataList,
                              Class<?> clazz,
                              Map<String, String> transMap,
                              MethodHandles.Lookup lookup,
                              Map<Field, MethodHandle> getterHandleMap,
                              Map<Field, MethodHandle> setterHandleMap) {
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
                    String res = transMap.get(fieldContent);
                    if (Objects.isNull(res)) {
                        continue;
                    }

                    MethodHandle setter = findSetter(lookup, setterHandleMap, clazz, field);
                    setter.invoke(row, res);
                } catch (Throwable e) {
                    // 失败跳过，保留原值
                    log.error("writeContent error", e);
                }
            }
        }
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
