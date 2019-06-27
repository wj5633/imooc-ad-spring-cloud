package com.wj5633.ad.client.vo;

import java.util.Date;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/1 22:24
 * @description
 */

@Data
public class AdPlan {

    private Long id;
    private Long userId;
    private String planName;
    private Integer planStatus;
    private Date startdate;
    private Date endDate;
    private Date updateTime;
    private Date createTime;
}
