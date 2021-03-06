package com.wj5633.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 22:38
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItResponse {

    private List<Long> ids;
}
