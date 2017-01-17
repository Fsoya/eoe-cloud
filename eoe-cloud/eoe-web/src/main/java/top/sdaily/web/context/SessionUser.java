package top.sdaily.web.context;

import top.sdaily.web.dto.UserMenu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by soya on 2016/12/16.
 */
public class SessionUser implements Serializable{

    private static final long serialVersionUID = -1L;

    private String token;
    private String name;
    private List<UserMenu> menus;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<UserMenu> menus) {
        this.menus = menus;
    }
}
