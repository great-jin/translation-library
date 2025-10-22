package xyz.ibudai.translation.web.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.ibudai.translation.web.basic.model.ResponseData;

@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    /**
     * 监听异常请求并处理返回
     */
    @ExceptionHandler(Exception.class)
    public ResponseData handleNotFoundException(Exception e) {
        log.error("Response error", e);
        return ResponseData.failed(e.getMessage());
    }
}
