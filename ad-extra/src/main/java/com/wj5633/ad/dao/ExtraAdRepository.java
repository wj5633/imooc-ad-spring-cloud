package com.wj5633.ad.dao;

import com.wj5633.ad.entity.ExtraAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created at 2019/8/11 14:50.
 *
 * @author wangjie
 * @version 1.0.0
 */

@Repository
public interface ExtraAdRepository extends JpaRepository<ExtraAd, Long> {
}
