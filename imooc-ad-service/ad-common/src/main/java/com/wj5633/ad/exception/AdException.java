package com.wj5633.ad.exception;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/29 22:37
 * @description
 */

@Data
public class AdException extends Exception {

    private Integer code;

    public AdException( String message) {
        super(message);
    }

    public AdException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
