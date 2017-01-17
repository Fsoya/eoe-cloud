package top.sdaily.web.dto;

import top.sdaily.web.model.Menu;
import top.sdaily.web.model.Right;

import javax.persistence.Transient;
import java.util.List;

/**
 * Created by soya on 2017/1/17.
 */
public class UserMenu extends Menu {

    private int badge;
    private List<Right> rights;
    @Transient
    private List<UserMenu> userMenus;

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }

    public List<UserMenu> getUserMenus() {
        return userMenus;
    }

    public void setUserMenus(List<UserMenu> userMenus) {
        this.userMenus = userMenus;
    }
}
