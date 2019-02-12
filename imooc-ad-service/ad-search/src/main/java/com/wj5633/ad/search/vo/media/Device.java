package com.wj5633.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午5:42
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    private String deviceCode;

    private String mac;
    private String ip;

    private String model;

    private String displaySize;
    private String screenSize;

    private String serialName;
}
