package com.wj5633.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 22:24
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {
    private Long planId;
    private String unitName;

    private Integer positionType;
    private Long budget;

    public boolean createValidate() {
        return planId != null && !StringUtils.isEmpty(unitName)
                && positionType != null && budget != null;
    }
}
