package com.bjpowernode.dao;

import com.bjpowernode.BaseTest;
import com.bjpowernode.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testAreaDao(){
        List<Area> listArea = areaDao.queryArea();

        assertEquals(3, listArea.size());
    }
}
