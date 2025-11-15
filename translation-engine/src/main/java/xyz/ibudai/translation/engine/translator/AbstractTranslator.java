package xyz.ibudai.translation.engine.translator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;
import xyz.ibudai.translation.engine.enums.DataType;
import xyz.ibudai.translation.engine.entity.dto.BatchReqDTO;
import xyz.ibudai.translation.engine.entity.dto.RequestDTO;
import xyz.ibudai.translation.engine.entity.dto.ResponseDTO;
import xyz.ibudai.translation.engine.entity.props.ResourceProps;
import xyz.ibudai.translation.engine.request.RequestClient;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public abstract class AbstractTranslator {

    protected final ResourceProps resourceProps;

    private final ObjectMapper objectMapper;
    private final RequestClient requestClient;


    public abstract String getApi();

    public abstract String getBatchApi();


    /**
     * 文本翻译
     *
     * @param reqDto the req dto
     * @return the response dto
     * @throws Exception the exception
     */
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

    /**
     * 批量文本翻译
     *
     * @param batchReqDTO the batch req dto
     * @return the list
     * @throws Exception the exception
     */
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
