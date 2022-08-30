package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;

import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    public User findByUsername(String username) {

        User user = userDao.findByUsername(username);
        if (user==null){
            return null;
        }
        Integer id = user.getId();
        Set<Role> roles = roleDao.findByUserId(id);
        for (Role role : roles) {
            Set<Permission> permissions = permissionDao.findByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        user.setRoles(roles);

        return user;
    }


}
