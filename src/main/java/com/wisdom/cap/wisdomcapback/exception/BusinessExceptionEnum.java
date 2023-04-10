package com.wisdom.cap.wisdomcapback.exception;

/**
 * @author 陈蒙欣
 * @date 2023/3/24 18:01
 */
public enum BusinessExceptionEnum {
    /**
     * 语音合成错误
     */
    VOICE_EXECUTION(10001 , "语音合成错误");
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;

    BusinessExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
