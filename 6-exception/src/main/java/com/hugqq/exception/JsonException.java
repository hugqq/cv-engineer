package com.hugqq.exception;

import com.hugqq.constant.Status;
import lombok.Getter;

/**
 * JSON异常
 */
@Getter
public class JsonException extends BaseException {

    public JsonException(Status status) {
        super(status);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
