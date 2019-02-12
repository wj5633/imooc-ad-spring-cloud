package com.wj5633.ad.search;

import com.wj5633.ad.search.vo.SearchRequest;
import com.wj5633.ad.search.vo.SearchResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午5:35
 * @description
 */

public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
