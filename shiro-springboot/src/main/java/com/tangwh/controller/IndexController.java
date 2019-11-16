package com.tangwh.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping({"/","index"})
    public String toIndex(Model model){
      model.addAttribute("msg", "Hello Shiro");
        return "index";
    }


    @RequestMapping("/user/add")
    public String add(){

        return "user/addIndex";
    }

    @RequestMapping("/user/update")
    public String update(){

        return "user/update";
    }

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }


    /**
     * 点击登录 之后
     * @param username
     * @param password
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username
            ,@RequestParam("password") String password
    ,Model model){

        //获取当前用户
        Subject subject = SecurityUtils.getSubject();

        // 封装用户的登录数据 获取Token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);//执行登录的方法 如果没有异常说明 ok


            return "index";
        }catch (UnknownAccountException uae){
            //用户名不存在 异常
      model.addAttribute("msg", "用户名不存在");
      return "login";
        }catch (IncorrectCredentialsException ice){
            // 密码不存在
            model.addAttribute("msg", "密码错误");
            return "login";
        }

    }

    /**
     * 跳转到未授权页面
     * @return
     */
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权 不能访问此页面";

    }
}
