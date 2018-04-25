package com.example.spring.boot.common.entity;

import java.io.Serializable;

/**
 * @author WQ[mikesdfd@gmail.com]
 * @className BaseEntity
 * @create 2018/04/24 15:05
 * @desc TODO  所有实体类的父类
 **/
public abstract class BaseEntity<ID extends Serializable> implements Serializable{

    /**
     * 主键
     */
    protected ID id ;

    /**
     * 创建时间
     */
    protected Long createTime;

    /**
     * 修改时间
     */
    protected Long updateTime;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
