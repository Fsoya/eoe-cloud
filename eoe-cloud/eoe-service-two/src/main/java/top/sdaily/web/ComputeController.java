package top.sdaily.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import top.sdaily.model.SysUser;
import top.sdaily.service.SysUserService;

import java.util.List;

/**
 * Created by soya on 2016/11/5.
 */
@RestController
public class ComputeController {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/sub" ,method = RequestMethod.GET)
    public Integer sub(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a - b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }

    /**
     * 测试 服务间的服务消费
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(value = "/a-add",method = RequestMethod.GET)
    public Integer addFromServiceA(@RequestParam Integer a, @RequestParam Integer b){
        return sysUserService.add(a,b);
    }

    @RequestMapping(value = "/a-user",method = RequestMethod.GET)
    public List<SysUser> getUser(){
        return sysUserService.getUser();
    }
}
