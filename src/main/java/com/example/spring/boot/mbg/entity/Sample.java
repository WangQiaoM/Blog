package com.example.spring.boot.mbg.entity;

import com.example.spring.boot.common.entity.BaseEntity;

/**
 * Sample
 * 
 * @author WangQiao[mikesdfd@gmail.com]
 * @date 2018/04/25 1843
 */
public class Sample extends BaseEntity<Long> {
    /**
     * Ãû³Æ
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}