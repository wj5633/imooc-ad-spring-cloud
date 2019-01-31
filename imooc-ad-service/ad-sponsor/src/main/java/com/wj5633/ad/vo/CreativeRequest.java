package com.wj5633.ad.vo;

import com.wj5633.ad.constant.CommonStatus;
import com.wj5633.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 23:06
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeRequest {

    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;


    public Creative convetToEntity() {
        Creative creative = new Creative();
        creative.setName(name);
        creative.setType(type);
        creative.setMaterialType(materialType);
        creative.setHeight(height);
        creative.setWidth(width);
        creative.setSize(size);
        creative.setDuration(duration);
        creative.setAuditStatus(CommonStatus.VALID.getStatus());
        creative.setUserId(userId);
        creative.setUrl(url);
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());
        return creative;
    }
}
