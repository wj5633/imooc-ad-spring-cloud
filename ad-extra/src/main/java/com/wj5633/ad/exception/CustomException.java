package com.wj5633.ad.exception;

/**
 * Created at 2019/8/11 14:51.
 *
 * @author wangjie
 * @version 1.0.0
 */

public class CustomException extends Exception {
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }
}
