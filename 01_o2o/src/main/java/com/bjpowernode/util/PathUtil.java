package com.bjpowernode.util;

import java.util.Locale;

public class PathUtil {

    private static String separator = System.getProperty("file.separator");

    /**
     * 获取图片根路径
     * @return
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase(Locale.ROOT).startsWith("win")) {
            basePath = "D:/projectdev/image/";
        } else {
            basePath = "/home/rei/o2o/images/";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    /**
     * 返回商户图片路径（子路径）
     */
    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/item/shop/" + shopId +"/";
        imagePath = imagePath.replace("/", separator);
        return imagePath;
    }
}
