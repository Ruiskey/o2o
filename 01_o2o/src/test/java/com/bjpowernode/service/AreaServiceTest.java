package com.bjpowernode.service;

import com.bjpowernode.BaseTest;
import com.bjpowernode.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void testAreaService(){
        List<Area> areaList = areaService.selectArea();
        assertEquals("老校区", areaList.get(0).getAreaName());
    }
}
