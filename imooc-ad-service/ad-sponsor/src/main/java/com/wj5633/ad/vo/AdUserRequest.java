package com.wj5633.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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
public class AdUserRequest {

    private Long id;
    private String username;

    public boolean createValidate() {
        return !StringUtils.isEmpty(username);
    }

    public boolean updateValidate() {
        return id != null && !StringUtils.isEmpty(username);
    }
}
