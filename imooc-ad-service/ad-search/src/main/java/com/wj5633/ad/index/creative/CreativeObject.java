package com.wj5633.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/2 0:07
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeObject {

    private Long adId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer width;
    private Integer height;
    private Integer auditStatus;
    private String adUrl;

    public void update(CreativeObject newObject) {
        if (newObject.getAdId() != null) {
            this.adId = newObject.getAdId();
        }
        if (newObject.getName() != null) {
            this.name = newObject.getName();
        }
        if (newObject.getType() != null) {
            this.type = newObject.getType();
        }
        if (newObject.getMaterialType() != null) {
            this.materialType = newObject.getMaterialType();
        }
        if (newObject.getWidth() != null) {
            this.width = newObject.getWidth();
        }
        if (newObject.getHeight() != null) {
            this.height = newObject.getHeight();
        }
        if (newObject.getAuditStatus() != null) {
            this.auditStatus = newObject.getAuditStatus();
        }
        if (newObject.getAdUrl() != null) {
            this.adUrl = newObject.getAdUrl();
        }

    }
}
