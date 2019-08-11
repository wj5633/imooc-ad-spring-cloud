package com.wj5633.ad.sender;

import com.wj5633.ad.dto.MySqlRowData;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午2:34
 * @description
 */

public interface ISender {

    void sender(MySqlRowData rowData);
}
