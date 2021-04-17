package com.bjpowernode.web.superadmin;

import com.bjpowernode.entity.Area;
import com.bjpowernode.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/arealist")
    @ResponseBody
    private Map<String, Object> listArea(){
        logger.info("===============start=======================");
        Map<String, Object> objectMap = new HashMap<String, Object>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            areaList = areaService.selectArea();
            objectMap.put("arealist", areaList);
            objectMap.put("total", areaList.size());
        } catch (Exception e){
            objectMap.put("success", false);
            objectMap.put("errMsg", e.toString());
        }
        logger.info("===============end=======================");
        return objectMap;
    }

}
