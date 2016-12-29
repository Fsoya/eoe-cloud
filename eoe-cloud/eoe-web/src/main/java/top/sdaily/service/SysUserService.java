package top.sdaily.service;

import top.sdaily.core.mybatis.Page;
import top.sdaily.model.SysUser;

import java.util.List;

/**
 * Created by soya on 2016/10/29.
 */
public interface SysUserService {

    List<SysUser> getAll();

    SysUser getOne();

    Page<SysUser> getPage(Page page);

    SysUser findById(String id);

    SysUser check(String userName,String password);

    List<SysUser> get(String userName);

    int addNew(SysUser sysUser);

    int addList(List<SysUser> sysUsers);

    int modify(SysUser sysUser);

    int delete(long id);

}
