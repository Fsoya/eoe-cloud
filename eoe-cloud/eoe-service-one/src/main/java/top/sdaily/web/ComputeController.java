package top.sdaily.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.sdaily.core.mybatis.Conditions;
import top.sdaily.mapper.SysUserMapper;
import top.sdaily.model.SysUser;

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
    private SysUserMapper sysUserMapper;

    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }

    @RequestMapping(value = "/user" ,method = RequestMethod.GET)
    public List<SysUser> getUser() {
        return sysUserMapper.findList(new Conditions(SysUser.class));
    }

    /*@Value("${from}")
    private String from;

    @GetMapping("/from")
    public String getConfig(){
        return from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }*/
}
