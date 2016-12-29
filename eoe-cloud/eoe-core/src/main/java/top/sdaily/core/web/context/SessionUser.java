package top.sdaily.core.web.context;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by soya on 2016/12/16.
 */
public class SessionUser implements Serializable{

    private static final long serialVersionUID = -1L;

    private String token;
    private String name;
    private List<Map> menus;

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

    public List<Map> getMenus() {
        return menus;
    }

    public void setMenus(List<Map> menus) {
        this.menus = menus;
    }
}
