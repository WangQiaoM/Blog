package com.example.spring.boot.mbg.repository;

import com.brtbeacon.common.mybatis.BaseMapper;
import com.brtbeacon.common.mybatis.annotation.MyBatisRepository;
import com.example.spring.boot.mbg.entity.Sample;
import com.example.spring.boot.mbg.entity.SampleExample;

/**
 * SampleMapper
 * 
 * @author WangQiao[mikesdfd@gmail.com]
 * @date 2018/04/25 1843
 */
@MyBatisRepository
public interface SampleMapper extends BaseMapper<Sample, SampleExample, Long> {
}