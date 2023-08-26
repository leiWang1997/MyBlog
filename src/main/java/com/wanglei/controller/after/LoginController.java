package com.wanglei.controller.after;

import com.wanglei.pojo.User;
import com.wanglei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 后台管理入口页面
     * @return
     */
    @GetMapping
    public String loginPages(){
        return "admin/login";
    }

    /**
     * 登录后跳转路径
     * @param username
     * @param password
     * @param httpSession
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession httpSession, RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);
            httpSession.setAttribute("user", user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message", "用户名密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 登出后跳转路径
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
