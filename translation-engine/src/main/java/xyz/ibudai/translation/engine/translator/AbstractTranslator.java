package xyz.ibudai.translation.engine.translator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;
import xyz.ibudai.translation.engine.enums.DataType;
import xyz.ibudai.translation.engine.model.BatchReqDTO;
import xyz.ibudai.translation.engine.model.EngineProps;
import xyz.ibudai.translation.engine.model.RequestDTO;
import xyz.ibudai.translation.engine.model.ResponseDTO;
import xyz.ibudai.translation.engine.request.RequestClient;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public abstract class AbstractTranslator {

    protected final EngineProps engineProps;

    private final ObjectMapper objectMapper;
    private final RequestClient requestClient;


    public abstract String getApi();

    public abstract String getBatchApi();


    public ResponseDTO translate(RequestDTO reqDto) throws Exception {
        if (Objects.isNull(reqDto)) {
            return null;
        }

        // 构建请求
        String data = objectMapper.writeValueAsString(reqDto);
        Request request = createReq(getApi(), data);


        // 请求引擎服务
        String result = requestClient.callRequest(request);

        // 解析内容
        if (Objects.isNull(result) || result.isEmpty()) {
            return null;
        }
        return objectMapper.readValue(result, ResponseDTO.class);
    }

    public List<ResponseDTO> batchTranslate(BatchReqDTO batchReqDTO) throws Exception {
        if (Objects.isNull(batchReqDTO)) {
            return null;
        }

        // 构建请求
        String data = objectMapper.writeValueAsString(batchReqDTO);
        Request request = createReq(getBatchApi(), data);

        // 请求引擎服务
        String result = requestClient.callRequest(request);

        // 解析内容
        if (Objects.isNull(result) || result.isEmpty()) {
            return null;
        }
        return objectMapper.readValue(result, new TypeReference<List<ResponseDTO>>() {
        });
    }


    private Request createReq(String url, String data) {
        MediaType mediaType = MediaType.parse(DataType.JSON.getType());
        return new Request.Builder()
                .url(url)
                .post(RequestBody.create(data, mediaType))
                .build();
    }
}
