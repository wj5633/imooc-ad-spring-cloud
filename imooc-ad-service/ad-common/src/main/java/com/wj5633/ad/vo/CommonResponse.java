package com.wj5633.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/29 22:16
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = 1;
    public static final String EMPTY_ERROR = "";

    private Integer code;
    private String error;
    private T data;

    public static <T> CommonResponse<T> of(int code, String error, T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setCode(code);
        response.setError(error);
        response.setData(data);
        return response;
    }

    public static <T> CommonResponse<T> ofSuccess(T data) {
        return CommonResponse.of(SUCCESS_CODE, EMPTY_ERROR, data);
    }

    public static <T> CommonResponse<T> ofSuccess() {
        return CommonResponse.ofSuccess(null);
    }

    public static <T> CommonResponse<T> ofError(int errorCode, String error) {
        return CommonResponse.of(errorCode, error, null);
    }

    public static <T> CommonResponse<T> ofError(String error) {
        return CommonResponse.ofError(ERROR_CODE, error);
    }
}
