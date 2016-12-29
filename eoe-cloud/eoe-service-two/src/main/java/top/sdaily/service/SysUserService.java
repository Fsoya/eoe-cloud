package top.sdaily.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.sdaily.hystrix.ComputeClientHystrix;
import top.sdaily.model.SysUser;

import java.util.List;

/**
 * Created by soya on 2016/11/9.
 */
@FeignClient(value = "compute-service-one",fallback = ComputeClientHystrix.class)
public interface SysUserService {


    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    List<SysUser> getUser();
}

