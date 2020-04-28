package com.wyfx.aw.controller;

import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.User;
import com.wyfx.aw.service.UserService;
import com.wyfx.aw.utils.RandomValidateCode;
import com.wyfx.aw.utils.WyfxContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/aw/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /*private String rightUserName ="admin";
    private String rightPassword ="admin123";*/


    @RequestMapping(value = "/sayHello",method = RequestMethod.POST)
    public Object sayHello(@RequestBody String message, HttpServletRequest request) {
        System.out.println("接收到参数:"+message);
        /*String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }*/
        String ip = request.getHeader("X-real-ip");
        System.out.println("X-real-ip:\n"+ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
         /*String ip = request.getHeader("x-forwarded-for");*/
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("WL-Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("HTTP_CLIENT_IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("HTTP_X_FORWARDED_FOR");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getRemoteAddr();
         }
        System.out.println("ipAddresses:\n"+ip);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"hello johnson");
    }


    /**
     * 生成验证码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/code",method = RequestMethod.GET)
    public Object code(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("image/jpeg");//设置响应类型，告知浏览器输出的是图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandomCode(request, response);//生成图片并通过response输出
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(String username,String password, String code){
        //session中获取验证码
        //String  sCode = WyfxContext.getSession().getAttribute("codeValidate").toString();
        if(code!=null){
            if(username==null){
                return  new ResponseEntity(ResponseCode.ERROR_IDENTIFY.getValue(),"用户名不存在");
            }
            User user= userService.findByNameAndPwd(username,password);
            if(password!=null && user!=null){
                WyfxContext.getSession().setAttribute("userName",username);
                return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
            }
            return  new ResponseEntity(ResponseCode.ERROR_IDENTIFY.getValue(),"用户名或密码错误");
        }else {
            return  new ResponseEntity(ResponseCode.ERROR_PARAM.getValue(),"验证码错误");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Object logout(HttpServletRequest request){
        request.getSession().invalidate();
        //重定向到登录页面
        return "";
    }
}
