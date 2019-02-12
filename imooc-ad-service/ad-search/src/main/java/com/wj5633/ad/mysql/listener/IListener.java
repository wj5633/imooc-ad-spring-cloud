package com.wj5633.ad.mysql.listener;

import com.wj5633.ad.mysql.dto.BinlogRowData;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午1:36
 * @description
 */
public interface IListener {

    void register();

    void onEvent(BinlogRowData eventData);
}
