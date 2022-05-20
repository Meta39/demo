package com.fu.demo.mapper;

import com.fu.demo.entity.Dept;
import com.fu.demo.entity.Menu;
import com.fu.demo.entity.Role;
import com.fu.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface LoginMapper {
    //登录时校验用户ID或用户名
    User checkUserId(@Param("userId") Long userId);

    //查询当前登录用户的部门组
    List<Dept> selectUserDepts(@Param("userId") Long userId);

    //查询当前登录用户的角色组
    List<Role> selectUserRoles(@Param("userId") Long userId);

    //查询当前登录用户对应的角色的菜单组
    Set<Menu> selectUserMenus(@Param("roles") List<Role> roles);
}
