package com.wyfx.aw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.AwGetWebsiteNews;
import com.wyfx.aw.service.AwTheCrawlerServer;
import com.wyfx.aw.service.OperationLoggingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: AwTheCrawlerController
 * @Description: 爬虫获取网站消息
 * @author: zhangguliang
 * @date: 2019-11-12
 */
@RestController
@RequestMapping("/aw/theCrawler")
public class AwTheCrawlerController {

    @Autowired
    private AwTheCrawlerServer awTheCrawlerServer;
    /**
     * 取网站信息
     * @return
     */
    @RequestMapping(value = "/getAwGetWebsiteNews",method = RequestMethod.GET)
    public Object getAwGetWebsiteNews(AwGetWebsiteNews awGetWebsiteNews) {
        if(null==awGetWebsiteNews){
            new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入serverId");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),awTheCrawlerServer.selectAwTheCrawler(awGetWebsiteNews));
    }
    /**
     * 添加网站信息
     * @param jsonAwGetWebsiteNews 以json获取网站信息
     * @return
     */
    @RequestMapping(value = "/addAwGetWebsiteNews",method = RequestMethod.POST)
    public Object addAwGetWebsiteNews(String jsonAwGetWebsiteNews) {
        System.out.println(JSONObject.parseObject(jsonAwGetWebsiteNews).size());
        System.out.println(JSONObject.parseObject(jsonAwGetWebsiteNews));
        int size = JSONObject.parseObject(jsonAwGetWebsiteNews).size();
         for (int i=0;i<size;i++){
            String string = JSONObject.parseObject(jsonAwGetWebsiteNews).get(i+1).toString();
            AwGetWebsiteNews result = JSON.parseObject(string, AwGetWebsiteNews.class);
            result.setId(null);
            if(!awTheCrawlerServer.addAwTheCrawler(result)){
                return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"添加失败");
            };
         }
       return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"添加成功");
    }
}
