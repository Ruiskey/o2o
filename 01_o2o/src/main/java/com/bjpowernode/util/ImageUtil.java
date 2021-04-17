package com.bjpowernode.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class ImageUtil {

    //获取当前线程的跟路径
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //时分秒
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    //创建一个日志对象
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将CommonsMultipartFile 转换成 File类
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 处理用户上传的缩略图，并返回新生成的图片的相对值路径
     * @return
     */
    public static String generateThumbnail(File thumbnail, String targetAddr) {
        try {
            basePath= URLDecoder.decode(basePath,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //生成文件随机名字
        String readFileName = getRandomFileName();
        //获得文件扩展名
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        //生成图片相对路径
        String relativeAddr = targetAddr + readFileName + extension;
        //建立debug信息
        logger.debug("current relativeAddr is :"+ relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        //建立debug信息
        logger.debug("current complete addr is:"+ PathUtil.getImgBasePath() + relativeAddr);
        try {
            System.out.println();
            Thumbnails.of(thumbnail).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();

        }

        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     * @param thumbnail
     * @return
     */
    private static String getFileExtension(File thumbnail) {
        String originalFileName = thumbnail.getName();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名字
     * 使用时分秒的方式 + 随机五位数  来保证
     * @return
     */
    public static String getRandomFileName() {
        //获取随机5位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException {
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        basePath= URLDecoder.decode(basePath,"utf-8");
        //  /home/rei/学习资料存放/java资料/018-SSM商铺系统/project/01_o2o/target/classes/watermark.jpg
        System.out.println(basePath + "watermark.jpg");
        Thumbnails.of(new File("/home/rei/o2o/images/mm2.jpg")).size(200,200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
                .outputQuality(0.8f).toFile("/home/rei/o2o/images/mm5.jpg");
    }
}
