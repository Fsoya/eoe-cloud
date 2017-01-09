package top.sdaily.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import top.sdaily.core.utils.IdGeneratorUtil;
import top.sdaily.core.web.exception.FailedException;
import top.sdaily.core.mybatis.Conditions;
import top.sdaily.core.mybatis.Page;
import top.sdaily.mapper.UserMapper;
import top.sdaily.model.User;
import top.sdaily.service.UserService;

import java.util.List;

/**
 * Created by soya on 2016/10/29.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.findList(new Conditions(User.class));
    }

    @Override
    public User getOne() {
        return userMapper.findOne(new Conditions(User.class));
    }

    @Override
    public Page<User> getPage(Page page) {
        List<User> pageResult = userMapper.findList(new Conditions(User.class).page(page));
        page.setResults(pageResult);
        return page;
    }

    @Override
    public User findById(String id) {
        return userMapper.findOne(new Conditions(User.class).id(id));
    }

    @Override
    public List<User> get(String userName) {
        userMapper.findList(new Conditions(User.class).like("userName","s"));
        userMapper.findList(new Conditions(User.class).lt("id","3"));
        userMapper.findList(new Conditions(User.class).lte("id","3"));
        userMapper.findList(new Conditions(User.class).in("id","3,4,5"));
        userMapper.findList(new Conditions(User.class).in("id",4,5,6,7));
        userMapper.findList(new Conditions(User.class).in("username","soya","showcase","sys"));
        userMapper.findList(new Conditions(User.class).gt("id","4"));
        userMapper.findList(new Conditions(User.class).gte("id","5"));
        return userMapper.findList(new Conditions(User.class).eq("userName",userName));
    }

    @Override
    public int addNew(User user) {
        user.setPkid(IdGeneratorUtil.fresh());
        return userMapper.insert(user);
    }

    @Override
    public int addList(List<User> users) {
        return userMapper.insertBatch(users);
    }

    @Override
    public int modify(User user) {
        return userMapper.update(user);
    }

    @Override
    public int delete(long id) {
        return userMapper.delete(new Conditions(User.class).id(id));
    }

    @Override
    public User check(String userName, String password) {
        User one = userMapper.findOne(new Conditions(User.class).eq("username", userName));
        if(one == null){
            throw new FailedException("用户不存在");
        }

        String encodePassword = DigestUtils.md5DigestAsHex((password + one.getSalt()).getBytes());

        if(!encodePassword.equals(one.getPassword())){
            throw new FailedException("密码不正确");
        }
        return one;
    }
}
