package top.sdaily.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import top.sdaily.core.web.exception.FailedException;
import top.sdaily.core.mybatis.Conditions;
import top.sdaily.core.mybatis.Page;
import top.sdaily.mapper.SysUserMapper;
import top.sdaily.model.SysUser;
import top.sdaily.service.SysUserService;

import java.util.List;

/**
 * Created by soya on 2016/10/29.
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getAll() {
        return sysUserMapper.findList(new Conditions(SysUser.class));
    }

    @Override
    public SysUser getOne() {
        return sysUserMapper.findOne(new Conditions(SysUser.class));
    }

    @Override
    public Page<SysUser> getPage(Page page) {
        List<SysUser> pageResult = sysUserMapper.findList(new Conditions(SysUser.class).page(page));
        page.setResults(pageResult);
        return page;
    }

    @Override
    public SysUser findById(String id) {
        return sysUserMapper.findOne(new Conditions(SysUser.class).id(id));
    }

    @Override
    public List<SysUser> get(String userName) {
        sysUserMapper.findList(new Conditions(SysUser.class).like("userName","s"));
        sysUserMapper.findList(new Conditions(SysUser.class).lt("id","3"));
        sysUserMapper.findList(new Conditions(SysUser.class).lte("id","3"));
        sysUserMapper.findList(new Conditions(SysUser.class).in("id","3,4,5"));
        sysUserMapper.findList(new Conditions(SysUser.class).in("id",4,5,6,7));
        sysUserMapper.findList(new Conditions(SysUser.class).in("username","soya","showcase","sys"));
        sysUserMapper.findList(new Conditions(SysUser.class).gt("id","4"));
        sysUserMapper.findList(new Conditions(SysUser.class).gte("id","5"));
        return sysUserMapper.findList(new Conditions(SysUser.class).eq("userName",userName));
    }

    @Override
    public int addNew(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    @Override
    public int addList(List<SysUser> sysUsers) {
        return sysUserMapper.insertBatch(sysUsers);
    }

    @Override
    public int modify(SysUser sysUser) {
        return sysUserMapper.update(sysUser);
    }

    @Override
    public int delete(long id) {
        return sysUserMapper.delete(new Conditions(SysUser.class).id(id));
    }

    @Override
    public SysUser check(String userName, String password) {
        SysUser one = sysUserMapper.findOne(new Conditions(SysUser.class).eq("username", userName));
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
