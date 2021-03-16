package com.gongjintao.blog.service;

import com.gongjintao.blog.bean.Role;
import com.gongjintao.blog.bean.User;
import com.gongjintao.blog.mapper.RolesMapper;
import com.gongjintao.blog.mapper.UserMapper;

import com.gongjintao.blog.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RolesMapper rolesMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null) {
            // 避免返回null，返回一个不含任何值的User对象，同样会密码验证失败
            return new User();
        }
        // 查询用户的角色信息，并返回存入user中
        List<Role> roles = rolesMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }

    public int updateUserEmail(String email) {
        return userMapper.updateUserEmail(email, Utils.getCurrentUser().getId());
    }

    public List<User> getUserByNickname(String nickname) {
        List<User> list = userMapper.getUserByNickname(nickname);
        return list;
    }

    public List<Role> getAllRole() {
        return userMapper.getAllRole();
    }

    public int updateUserEnabled(Boolean enabled, Long uid) {
        return userMapper.updateUserEnabled(enabled, uid);
    }

    public int deleteUserById(Long uid) {
        return userMapper.deleteUserById(uid);
    }

    public int updateUserRoles(Long[] rids, Long id) {
        int i = userMapper.deleteUserRolesByUid(id);
        return userMapper.setUserRoles(rids, id);
    }

    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }




    public int reg(User user) {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);//用户可用
        long result = userMapper.reg(user);
        //配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        int i = rolesMapper.addRoles(roles, user.getId());
        boolean b = i == roles.length && result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }
}
