package com.hugqq.exception;

import com.hugqq.constant.Status;
import lombok.Getter;

/**
 * 页面异常
 */
@Getter
public class PageException extends BaseException {

    public PageException(Status status) {
        super(status);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
