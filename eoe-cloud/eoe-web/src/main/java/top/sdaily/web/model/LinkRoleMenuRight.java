package top.sdaily.web.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by soya on 2017/1/9.
 */
@Table(name = "sys_role_menu_right")
public class LinkRoleMenuRight implements Serializable {

    @Id
    private String pkid;

    @Column(name = "role_menu_id")
    private String roleMenuId;

    @Column(name = "right_id")
    private String rightId;

    private boolean delete;

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(String roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public String getRightId() {
        return rightId;
    }

    public void setRightId(String rightId) {
        this.rightId = rightId;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
