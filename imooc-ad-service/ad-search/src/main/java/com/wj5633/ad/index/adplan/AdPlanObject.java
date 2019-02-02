package com.wj5633.ad.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/1 22:57
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanObject {

    private Long planId;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;

    public void update(AdPlanObject newObject) {
        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }
        if (newObject.getUserId() != null) {
            this.userId = newObject.getUserId();
        }
        if (newObject.getPlanStatus() != null) {
            this.planStatus = newObject.getPlanStatus();
        }
        if (newObject.getStartDate() != null) {
            this.startDate = newObject.getStartDate();
        }
        if (newObject.getStartDate() != null) {
            this.endDate = newObject.getEndDate();
        }
    }
}
