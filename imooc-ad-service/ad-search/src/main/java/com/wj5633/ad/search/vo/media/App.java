package com.wj5633.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午5:40
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

    private String appCode;

    private String appName;

    private String packageName;

    private String activityName;
}
