package top.sdaily.web.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sdaily.web.context.ReturnBody;
import top.sdaily.web.service.MenuService;

/**
 * Created by soya on 2017/1/19.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping
    public ReturnBody getMenus(){
        return ReturnBody.success().setData(menuService.findAll());
    }


}
