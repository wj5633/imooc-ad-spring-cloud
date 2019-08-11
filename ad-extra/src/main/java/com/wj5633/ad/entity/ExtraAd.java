package com.wj5633.ad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created at 2019/8/11 14:48.
 *
 * @author wangjie
 * @version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ExtraAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    public ExtraAd(String name) {
        this.name = name;
    }

}
