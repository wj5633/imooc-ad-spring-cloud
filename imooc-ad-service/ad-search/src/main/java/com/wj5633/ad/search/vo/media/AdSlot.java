package com.wj5633.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午5:38
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    private String adSlotCode;

    private Integer positionType;

    private Integer width;
    private Integer height;

    private List<Integer> types;

    private Integer minCpm;
}
