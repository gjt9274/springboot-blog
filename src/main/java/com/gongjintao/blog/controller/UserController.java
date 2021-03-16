package com.gongjintao.blog.controller;

import com.gongjintao.blog.bean.RespBean;
import com.gongjintao.blog.service.UserService;
import com.gongjintao.blog.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/currentUserName")
    public String currentUserName() {
        return Utils.getCurrentUser().getNickname();
    }

    @RequestMapping("/currentUserId")
    public Long currentUserId() {
        return Utils.getCurrentUser().getId();
    }

    @RequestMapping("/currentUserEmail")
    public String currentUserEmail() {
        return Utils.getCurrentUser().getEmail();
    }

//    @RequestMapping("/isAdmin")
//    public Boolean isAdmin() {
//        List<GrantedAuthority> authorities = Utils.getCurrentUser().getAuthorities();
//        for (GrantedAuthority authority : authorities) {
//            if (authority.getAuthority().contains("超级管理员")) {
//                return true;
//            }
//        }
//        return false;
//    }

    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    public RespBean updateUserEmail(String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean("success", "开启成功!");
        }
        return new RespBean("error", "开启失败!");
    }
}
