package com.fu.demo.service;

import com.fu.demo.entity.Menu;
import com.fu.demo.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

