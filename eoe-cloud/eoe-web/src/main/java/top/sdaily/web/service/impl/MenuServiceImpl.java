package top.sdaily.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.sdaily.core.mybatis.Conditions;
import top.sdaily.mapper.MenuMapper;
import top.sdaily.web.dto.UserMenu;
import top.sdaily.web.model.Menu;
import top.sdaily.web.service.MenuService;

import java.util.List;

/**
 * Created by soya on 2016/10/29.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<UserMenu> getUserMenus(String userId) {
        List<UserMenu> rootMenus = menuMapper.findChildren("0");
        iterateMenus(rootMenus);

        return rootMenus;
    }

    private void iterateMenus(List<UserMenu> baseMenus){
        for (UserMenu menu : baseMenus) {
            List<UserMenu> list = menuMapper.findChildren(menu.getPkid());
            if(!CollectionUtils.isEmpty(list)){
                iterateMenus(list);
                menu.setUserMenus(list);
            }
        }
    }

    @Override
    public List<Menu> findAll() {
        return menuMapper.findTree();
    }
}
