package com.wj5633.ad.dao;

import com.wj5633.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 1:10
 * @description
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    AdUser findByUsername(String username);
}
