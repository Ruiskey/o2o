package com.bjpowernode.service.impl;

import com.bjpowernode.dao.AreaDao;
import com.bjpowernode.entity.Area;
import com.bjpowernode.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> selectArea() {
        return areaDao.queryArea();
    }
}
