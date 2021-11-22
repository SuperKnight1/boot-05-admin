package com.study.boot05.controller;

import com.study.boot05.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @GetMapping(value = {"/login","/"})
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    //鼠标点击对勾登录，发送了一个post的请求，发送了这个请求重定向到main.html 发送这个请求之后已经：http://localhost:8080/main.html
    public String main(User user, HttpSession session, Model model){
        //判断如果用户名是空或者密码不是123456
        if(!ObjectUtils.isEmpty(user.getUsername()) && "123456".equals(user.getPassword())){
            //将登陆的用户名字放在session中
            session.setAttribute("loginName",user);
            //跳转到redir  main.html
            return "redirect:/main.html";
        }else {
            //否则的话就是用户名为空或者密码不是123456就重新返回登陆页面
            model.addAttribute("msg","账号密码错误");
            return  "login";
        }
    }

    //去main页面真正刷新的是这个请求：刷新的是http://localhost:8080/main.html这个请求，防止表单重复提交，
    @GetMapping("main.html")
    public String main(HttpSession session,Model model){

        Object loginUser = session.getAttribute("loginName");
        if(loginUser!=null){
            //查看session中是否有登陆的用户是如果有返回main页面
            return "main";
        }else {
            //没有，则返回登陆页面
            model.addAttribute("msg","未登录，请重新登录");
            return "login";
        }
    }
    //去main页面真正刷新的是这个请求
    @PostMapping("main.html")
    public String test(){
        return "redirect:404.html";
    }
}
