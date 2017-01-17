package top.sdaily.web.service;

import top.sdaily.core.mybatis.Page;
import top.sdaily.web.model.User;

import java.util.List;

/**
 * Created by soya on 2016/10/29.
 */
public interface UserService {

    List<User> getAll();

    User getOne();

    Page<User> getPage(Page page);

    User findById(String id);

    User check(String userName, String password);

    List<User> get(String userName);

    int addNew(User user);

    int addList(List<User> users);

    int modify(User user);

    int delete(long id);

}
