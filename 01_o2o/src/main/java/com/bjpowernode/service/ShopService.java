package com.bjpowernode.service;

import com.bjpowernode.entity.Shop;
import com.bjpowernode.exceptions.ShopOperationException;
import com.bjpowernode.vo.ShopExecution;

import java.io.File;

public interface ShopService {
    ShopExecution addShop(Shop shop, File shopImg) throws ShopOperationException;
}
