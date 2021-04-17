package com.bjpowernode.dao;

import com.bjpowernode.BaseTest;
import com.bjpowernode.entity.Area;
import com.bjpowernode.entity.PersonInfo;
import com.bjpowernode.entity.Shop;
import com.bjpowernode.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testInsertShop(){
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        Shop shop = new Shop();
        personInfo.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(personInfo);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("奶茶店铺");
        shop.setShopDesc("奶茶店铺");
        shop.setShopImg("test");
        shop.setShopAddr("test");
        shop.setEnableStatus(1);
        int effectNums = shopDao.insertShop(shop);
        assertEquals(1, effectNums);
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(2L);
        shop.setShopName("奶茶店铺2");
        shop.setShopImg("test");
        shop.setShopAddr("test");
        shop.setEnableStatus(1);
        int effectNums = shopDao.updateShop(shop);
        assertEquals(1, effectNums);
    }
}
