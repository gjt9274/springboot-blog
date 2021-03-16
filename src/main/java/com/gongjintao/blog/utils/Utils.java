package com.gongjintao.blog.utils;

import com.gongjintao.blog.bean.User;

public class Utils {
    public static User getCurrentUser() {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return user;
        User user = new User();
        user.setId(6L);
        return user;
    }
}
