package com.bjpowernode.web.shopadmin;

import com.bjpowernode.entity.PersonInfo;
import com.bjpowernode.entity.Shop;
import com.bjpowernode.enums.ShopStateEnum;
import com.bjpowernode.service.ShopService;
import com.bjpowernode.util.HttpServletRequestUtil;
import com.bjpowernode.util.ImageUtil;
import com.bjpowernode.util.PathUtil;
import com.bjpowernode.vo.ShopExecution;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request){
        Map<String, Object> objectMap = new HashMap<String, Object>();
        //1 接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //将shopStr转化为实体类
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            objectMap.put("success", false);
            objectMap.put("errMsg", e.getMessage());
            return objectMap;
        }
        //处理图片相关的信息 (CommonsMultipartFile)是spring能够处理的文件流
        CommonsMultipartFile shopImg = null;
        //从本次request中的上下文环境获取的内容
        CommonsMultipartResolver commonsMultipartResolver = new
                CommonsMultipartResolver(request.getSession().getServletContext());
        //判断本次的request中是否有上传的文件流
        if(commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            objectMap.put("success", false);
            objectMap.put("errMsg", "上传图片不能为空");
            return objectMap;
        }
        //2 注册店铺
        if( shop != null && shopImg != null) {
            //这里的owner信息 可以通过在session中获取
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                objectMap.put("success", false);
                objectMap.put("errMsg", e.getMessage());
                return objectMap;
            }
            try {
                //转换文件格式 ，将CommonsMultipartFile类型通过inputStream转换为 File类型
                inputStreamToFile(shopImg.getInputStream(), shopImgFile);
            } catch (IOException e) {
                objectMap.put("success", false);
                objectMap.put("errMsg", e.getMessage());
                return objectMap;
            }
            //进行店铺注册 并返回结果
            ShopExecution shopExecution = shopService.addShop(shop, shopImgFile);
            //如果返回的注册信息为CHECK， 则将objectMap中添加true信息
            if(shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                objectMap.put("success", true);
            } else {
                objectMap.put("success", false);
                objectMap.put("errMsg", shopExecution.getStateInfo());
            }
            return objectMap;
        } else {
            objectMap.put("success", false);
            objectMap.put("errMsg", "请输入店铺信息");
            return objectMap;
        }


    }

    /**
     * 构建一个转换文件格式的方法
     */
    private static void inputStreamToFile(InputStream in, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) != -1){
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用inputStreamToFile产生异常:" + e.getMessage());
        } finally {
            //关闭输入流 和 输出流
            try {
                if(os != null) {
                    os.close();
                }
                if(in != null) {
                    in.close();
                }
            }catch (Exception e) {
                throw new RuntimeException("关闭输入流和输出流产生异常:" + e.getMessage());
            }
        }
    }
}

















