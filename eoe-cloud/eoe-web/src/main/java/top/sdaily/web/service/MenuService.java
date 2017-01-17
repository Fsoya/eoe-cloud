package top.sdaily.web.service;

import top.sdaily.web.dto.UserMenu;

import java.util.List;

/**
 * Created by soya on 2016/10/29.
 */
public interface MenuService {
    List<UserMenu> getUserMenus(String userId);
}
