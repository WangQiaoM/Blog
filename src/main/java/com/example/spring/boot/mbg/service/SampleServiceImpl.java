package com.example.spring.boot.mbg.service;

import com.brtbeacon.common.service.BaseServiceSupport;
import com.example.spring.boot.mbg.entity.Sample;
import com.example.spring.boot.mbg.entity.SampleExample;
import com.example.spring.boot.mbg.repository.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SampleServiceImpl
 * 
 * @author WangQiao[mikesdfd@gmail.com]
 * @date 2018/04/25 1843
 */
@Service
public class SampleServiceImpl extends BaseServiceSupport<Sample, SampleExample, Long> implements SampleService {
    private SampleMapper mapper;

    @Autowired
    public void setMapper(SampleMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public SampleMapper getMapper() {
        return this.mapper;
    }
}