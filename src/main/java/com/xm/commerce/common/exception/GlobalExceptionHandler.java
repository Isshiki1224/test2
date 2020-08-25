package com.xm.commerce.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @ExceptionHandler(value = com.xm.commerce.common.exception.BaseException.class)
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
    public ResponseEntity<ErrorResponse> handleException(MaxUploadSizeExceededException ex, HttpServletRequest request) throws Exception {
        long maxUploadSize = ex.getMaxUploadSize();
        log.error("大小超出限制,最大:" + maxUploadSize);
        ErrorResponse errorResponse = new ErrorResponse(ExceptionCode.MAX_UPLOAD_SIZE, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
