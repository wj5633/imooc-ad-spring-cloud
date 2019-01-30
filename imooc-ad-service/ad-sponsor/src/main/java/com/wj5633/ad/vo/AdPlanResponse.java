package com.wj5633.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 1:23
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanResponse {

    private Long id;
    private Long userId;
    private String planName;
}
