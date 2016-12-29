package top.sdaily.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.sdaily.model.Menu;
import top.sdaily.model.SysUser;
import top.sdaily.service.SysUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soya on 2016/11/14.
 */
@RequestMapping
@Controller
public class WebController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/")
    public String index(ModelMap map) {

        List<Menu> menus = new ArrayList();

        Menu menu1 = new Menu();
        menu1.setIcon("fa-home");
        menu1.setName("首页");
        menu1.setUrl("main");
        menus.add(menu1);

        Menu menu2 = new Menu();
        menu2.setIcon("fa-user");
        menu2.setName("用户");
        menu2.setUrl("");

        List<Menu> subMenus = new ArrayList();
        Menu menu21 = new Menu();
        menu21.setName("用户列表");
        menu21.setUrl("users");
        subMenus.add(menu21);

        Menu menu22 = new Menu();
        menu22.setName("用户列表");
        menu22.setUrl("users");
        subMenus.add(menu22);
        menu2.setSubMenus(subMenus);
        menus.add(menu2);

        Menu menu3 = new Menu();
        menu3.setIcon("fa-list");
        menu3.setName("菜单");
        menu3.setUrl("menu");
        menus.add(menu3);

        map.addAttribute("menus", menus);

        return "index";
    }

    @GetMapping("/main")
    public String viewMain(ModelMap map) {
        return "main/main";
    }

    @GetMapping("/users")
    public String viewUser(ModelMap map) {
        List<SysUser> sysUsers = sysUserService.getAll();
        map.addAttribute("sysUsers", sysUsers);
        return "user/users";
    }

    @GetMapping("/users/{id}")
    public String viewMain1(ModelMap map, @PathVariable String id) {
        SysUser user = sysUserService.findById(id);
        map.addAttribute("user",user);
        return "user/userDetail";
    }

    @GetMapping("/main3")
    public String viewMain3(ModelMap map) {
        return "main/main3";
    }

    @GetMapping("sysusers/one")
    public String getSysUsersOne(ModelMap map) {
        SysUser one = sysUserService.getOne();
        map.addAttribute("sysUser", one);
        return "index";
    }

    @GetMapping("sysusers")
    public String getSysUsers(ModelMap map) {
        List<SysUser> sysUsers = sysUserService.getAll();
        map.addAttribute("sysUsers", sysUsers);
        return "index";
    }

}
