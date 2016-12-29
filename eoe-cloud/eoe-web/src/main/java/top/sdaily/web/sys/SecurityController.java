package top.sdaily.web.sys;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.sdaily.core.utils.IdGeneratorUtil;
import top.sdaily.core.web.ReturnBody;
import top.sdaily.core.web.context.SessionUser;
import top.sdaily.model.SysUser;
import top.sdaily.service.SysUserService;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by soya on 2016/12/13.
 */
@RestController
public class SecurityController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value="密码账号登录", notes="密码账号登录")
    @PostMapping("login")
    public ReturnBody login(@ApiParam(required = true,name = "sysUser") @RequestBody SysUser sysUser){
        SysUser one = sysUserService.check(sysUser.getUserName(), sysUser.getPassword());
        SessionUser sessionUser = new SessionUser();
        sessionUser.setToken(IdGeneratorUtil.fresh());
        sessionUser.setName(one.getUserName());

        List<Map> menus = new ArrayList<Map>();
        Map menu = new HashMap();
        menu.put("name","系统管理");

        List<Map> subMenus = new ArrayList<Map>();
        Map subMenu = new HashMap();
        subMenu.put("name","用户管理");
        subMenu.put("url","/user");
        subMenu.put("badge",6);

        List rights = new ArrayList<String>();
        rights.add("add");
        rights.add("edit");
        rights.add("query");
        subMenu.put("rights",rights);

        Map subMenu1 = new HashMap();
        subMenu1.put("name","测试");
        subMenu1.put("url","/user1");
        subMenu1.put("badge",0);

        subMenus.add(subMenu);
        subMenus.add(subMenu1);

        menu.put("subMenus",subMenus);

        menus.add(menu);

        sessionUser.setMenus(menus);

        stringRedisTemplate.opsForValue().set(sessionUser.getToken(), JSON.toJSONString(sessionUser));
        //========================
        // token 有效期 30 分钟
        //========================
        stringRedisTemplate.expire(sessionUser.getToken(),30, TimeUnit.MINUTES);

        return ReturnBody.success().setData(sessionUser);
    }

}
