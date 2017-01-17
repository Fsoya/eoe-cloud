package top.sdaily.web.controller.sys;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.sdaily.core.utils.IdGeneratorUtil;
import top.sdaily.web.context.ReturnBody;
import top.sdaily.web.context.SessionUser;
import top.sdaily.web.dto.UserMenu;
import top.sdaily.web.model.Menu;
import top.sdaily.web.model.User;
import top.sdaily.web.service.MenuService;
import top.sdaily.web.service.UserService;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by soya on 2016/12/13.
 */
@RestController
public class SecurityController {

    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value="密码账号登录", notes="密码账号登录")
    @PostMapping("login")
    public ReturnBody login(@ApiParam(required = true,name = "user") @RequestBody User user){
        User one = userService.check(user.getUserName(), user.getPassword());
        SessionUser sessionUser = new SessionUser();
        sessionUser.setToken(IdGeneratorUtil.fresh());
        sessionUser.setName(one.getUserName());

        List<UserMenu> userMenus = menuService.getUserMenus(one.getPkid());

//        List<Map> menus = new ArrayList<Map>();
//        Map menu = new HashMap();
//        menu.put("name","系统管理");
//
//        List<Map> subMenus = new ArrayList<Map>();
//
//        Map subMenu = new HashMap();
//        subMenu.put("name","用户管理");
//        subMenu.put("url","/user");
//        subMenu.put("badge",6);
//
//        List rights = new ArrayList<String>();
//        rights.add("add");
//        rights.add("edit");
//        rights.add("query");
//        subMenu.put("rights",rights);
//
//        Map subMenu1 = new HashMap();
//        subMenu1.put("name","菜单管理");
//        subMenu1.put("url","/menu");
//        subMenu1.put("badge",0);
//
//        List rights1 = new ArrayList<String>();
//        rights1.add("add");
//        rights1.add("edit");
//        rights1.add("query");
//        subMenu1.put("rights",rights1);
//
//        subMenus.add(subMenu);
//        subMenus.add(subMenu1);
//
//        menu.put("subMenus",subMenus);
//
//        menus.add(menu);

        sessionUser.setMenus(userMenus);

        stringRedisTemplate.opsForValue().set(sessionUser.getToken(), JSON.toJSONString(sessionUser));
        //========================
        // token 有效期 30 分钟
        //========================
        stringRedisTemplate.expire(sessionUser.getToken(),30, TimeUnit.MINUTES);

        return ReturnBody.success().setData(sessionUser);
    }

    @ApiOperation(value="验证token", notes="验证token")
    @GetMapping("token/{token}")
    public ReturnBody validToken(@ApiParam(required = true,name = "token") @PathVariable String token){
        String jsonString = stringRedisTemplate.opsForValue().get(token);
        SessionUser sessionUser = null;
        if(!StringUtils.isEmpty(jsonString)) {
            sessionUser = JSON.parseObject(stringRedisTemplate.opsForValue().get(token), SessionUser.class);
        }
        return ReturnBody.success().setData(sessionUser);
    }

}
