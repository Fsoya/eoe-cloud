package top.sdaily.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sdaily.core.mybatis.Page;
import top.sdaily.core.web.context.SessionUser;
import top.sdaily.model.SysUser;
import top.sdaily.service.SysUserService;

import java.util.List;

/**
 * Created by soya on 2016/10/28.
 */
@RestController
@RequestMapping(value = "eoe")
public class EoeController {

    @Autowired
    SysUserService sysUserService;

    @ApiOperation(value="EOE 欢迎", notes="使用示例")
    @RequestMapping(method = RequestMethod.GET)
    public String hiEoe(){
        return "Hi ~~ EOE!!!";
    }

    @ApiOperation(value="测试mybatis", notes="列表查询")
    @RequestMapping(value = "mybatis/sysuser",method = RequestMethod.GET)
    public List<SysUser> callMyBatisList(){
        return sysUserService.getAll();
    }


    @ApiOperation(value="测试mybatis", notes="单对象查询")
    @RequestMapping(value = "mybatis/sysuser/one",method = RequestMethod.GET)
    public SysUser callMyBatisOne(){
        return sysUserService.getOne();
    }


    @ApiOperation(value="测试mybatis", notes="分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNo",value = "页数",required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize",value = "每页记录数,默认15条",required = false)})
    @RequestMapping(value = "mybatis/sysuser/page",method = RequestMethod.GET)
    public Page<SysUser> callMyBatisWithPage(Page page, SessionUser sessionUser){
        System.out.println(sessionUser.getToken());
        return sysUserService.getPage(page);
    }

    @ApiOperation(value="测试mybatis", notes="条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",name = "id",value = "用户名",required = true)})
    @RequestMapping(value = "mybatis/sysuser/{id}",method = RequestMethod.GET)
    public SysUser callMyBatisWithCondition(@PathVariable String id){
        return sysUserService.findById(id);
    }

    @ApiOperation(value="测试mybatis", notes="单条插入")
    @RequestMapping(value = "mybatis/sysuser",method = RequestMethod.POST)
    public SysUser callMyBatisWithAdd(@ApiParam(required = true,name = "sysUser") @RequestBody SysUser sysUser){
        sysUserService.addNew(sysUser);
        return sysUser;
    }

    @ApiOperation(value="测试mybatis", notes="批量增加")
    @RequestMapping(value = "mybatis/sysuser/batch",method = RequestMethod.POST)
    public int callMyBatisWithAddList(@ApiParam(required = true,name = "sysUsers") @RequestBody List<SysUser> sysUsers){
        return sysUserService.addList(sysUsers);
    }

    @ApiOperation(value="测试mybatis", notes="单条插入")
    @RequestMapping(value = "mybatis/sysuser",method = RequestMethod.PUT)
    public int callMyBatisWithModify(@ApiParam(required = true,name = "sysUser") @RequestBody SysUser sysUser){
        return sysUserService.modify(sysUser);
    }


    @ApiOperation(value="测试mybatis", notes="删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id",value = "id",required = true)})
    @RequestMapping(value = "mybatis/sysuser",method = RequestMethod.DELETE)
    public int callMyBatisWithDelete(long id){
        return sysUserService.delete(id);
    }

}
