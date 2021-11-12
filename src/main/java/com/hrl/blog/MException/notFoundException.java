package com.hrl.blog.MException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//设置这个异常为资源找不到型异常
@ResponseStatus(HttpStatus.NOT_FOUND)
public class notFoundException extends RuntimeException {
    public notFoundException() {
    }

    public notFoundException(String message) {
        super(message);
    }

    public notFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
