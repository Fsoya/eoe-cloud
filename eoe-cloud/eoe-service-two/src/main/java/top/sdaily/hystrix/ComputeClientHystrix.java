package top.sdaily.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import top.sdaily.model.SysUser;
import top.sdaily.service.SysUserService;

import java.util.List;

/**
 * Created by soya on 2016/11/8.
 */
@Component
public class ComputeClientHystrix implements SysUserService {

    @Override
    public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        return -1;
    }

    @Override
    public List<SysUser> getUser() {
        return null;
    }
}
