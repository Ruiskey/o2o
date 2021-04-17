package com.bjpowernode.service.impl;

import com.bjpowernode.dao.ShopDao;
import com.bjpowernode.entity.Shop;
import com.bjpowernode.enums.ShopStateEnum;
import com.bjpowernode.exceptions.ShopOperationException;
import com.bjpowernode.service.ShopService;
import com.bjpowernode.util.ImageUtil;
import com.bjpowernode.util.PathUtil;
import com.bjpowernode.vo.ShopExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    //添加事务支持
    @Transactional
    public ShopExecution addShop(Shop shop, File shopImg){

        //1 首先判断shop是否为空
        if( shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        //确认非空之后，加入try catch语句中
        try {
            //2 给待插入的店铺信息设置初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //3 插入shop数据
            int resultNum = shopDao.insertShop(shop);
            if (resultNum < 1) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                //4 存储图片
                if (shopImg != null) {
                    try {
                        addShopImg(shop, shopImg);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    //5 更新店铺的图片地址
                    int effectedNum = shopDao.updateShop(shop);
                    if (effectedNum < 1) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException ("addShop error:"+ e.getMessage());
        }

        //确认一切OK 则返回一个ShopExecution对象
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, File shopImg) {
        //获取shop图片目录的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        //将shop中的shopimg 存储为相对路径地址
        shop.setShopImg(shopImgAddr);
    }
}
