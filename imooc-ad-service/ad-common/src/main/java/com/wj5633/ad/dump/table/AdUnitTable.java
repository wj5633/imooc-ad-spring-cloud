package com.wj5633.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-2 下午1:47
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitTable {

    private Long uintId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
}
