package com.fu.demo.service;

import com.fu.demo.entity.Menu;
import com.fu.demo.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class MenuService {
    @Resource
    private MenuMapper menuMapper;

    //根据ID查询
    public Menu select(Long id) {
        return menuMapper.select(id);
    }

    //查询全部
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }

    //新增
    public Integer insert(Menu menu) {
        return menuMapper.insert(menu);
    }

    //更新
    public Integer update(Menu menu) {
        return menuMapper.update(menu);
    }

    //删除
    public Integer delete(Long id) {
        return menuMapper.delete(id);
    }
    //获取菜单树
    public List<Menu> menuTree() {
        List<Menu> menus = menuMapper.selectAll();//获取数据库全部菜单
        return menus.stream().filter(m -> m.getPId() == 0).peek((m) -> m.setChildList(getMenuChildren(m, menus))).collect(Collectors.toList());
    }

    //获取菜单子列表
    private List<Menu> getMenuChildren(Menu parent, List<Menu> menuDatas) {
        return menuDatas.stream().filter(m -> Objects.equals(m.getPId(), parent.getId())).peek((m) -> m.setChildList(getMenuChildren(m, menuDatas))).collect(Collectors.toList());
    }
}

