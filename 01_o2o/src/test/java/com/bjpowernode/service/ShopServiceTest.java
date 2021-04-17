package com.bjpowernode.service;

import com.bjpowernode.BaseTest;
import com.bjpowernode.entity.Area;
import com.bjpowernode.entity.PersonInfo;
import com.bjpowernode.entity.Shop;
import com.bjpowernode.entity.ShopCategory;
import com.bjpowernode.enums.ShopStateEnum;
import com.bjpowernode.vo.ShopExecution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import static org.junit.Assert.assertEquals;

@Service
public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop(){
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        Shop shop = new Shop();
        personInfo.setUserId(1L);
        area.setAreaId(3);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(personInfo);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("奶茶店铺3");
        shop.setShopDesc("奶茶店铺3");
        shop.setShopAddr("test3");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        //初始化一个文件
        File file = new File("/home/rei/o2o/images/mm.png");
        ShopExecution shopExecution = shopService.addShop(shop, file);
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }
}
