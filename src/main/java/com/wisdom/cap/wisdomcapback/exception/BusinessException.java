package com.wisdom.cap.wisdomcapback.exception;

/**
 * @author 陈蒙欣
 * @date 2023/3/24 17:51
 */
public class BusinessException extends RuntimeException{
    private final Integer code;
    private final String message;

    public BusinessException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        this(businessExceptionEnum.getCode() , businessExceptionEnum.getMessage());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
