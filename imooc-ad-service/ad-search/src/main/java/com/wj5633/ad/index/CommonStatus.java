package com.wj5633.ad.index;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午6:50
 * @description
 */

@Getter
public enum CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }}
