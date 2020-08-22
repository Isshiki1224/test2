package com.xm.commerce.system.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    
    /**
     * BaseException异常处理
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ErrorResponse> ResourceNotFoundExceptionHandler(HttpServletRequest req, BaseException e){
        ErrorResponse errorResponse = new ErrorResponse(e, req.getRequestURI());
        log.error("occur BaseException:" + errorResponse.toString());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    /**
     * 请求参数异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>(8);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse errorResponse = new ErrorResponse(ExceptionCode.METHOD_ARGUMENT_NOT_VALID, request.getRequestURI(), errors);
//        log.error("occur MethodArgumentNotValidException:" + errorResponse.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    /**
     * 文件大小异常处理
     * @param ex
     * @param response
     * @throws Exception
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void handleException(MaxUploadSizeExceededException ex, HttpServletResponse response) throws Exception {
        StringBuilder error = new StringBuilder();
        if (ex != null) {
            SizeLimitExceededException cause = (SizeLimitExceededException) ex.getCause();
            long maxUploadSize = ex.getMaxUploadSize();
            String actualSize = String.valueOf(cause.getActualSize());
            double parseDouble = Double.parseDouble(actualSize) / 1024 / 1024;
            BigDecimal b = new BigDecimal(parseDouble);
            double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            error.append("最大上传文件为:" + maxUploadSize / 1024 / 1024).append("M;");
            error.append("实际文件大小为：").append(d).append("M");
            System.out.println(error.toString());
        }
        error.append("上传文件出错");
        System.out.println(error.toString());
    }

}
