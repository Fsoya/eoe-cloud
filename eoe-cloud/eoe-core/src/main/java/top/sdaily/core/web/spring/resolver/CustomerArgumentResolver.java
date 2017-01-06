package top.sdaily.core.web.spring.resolver;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import top.sdaily.core.mybatis.Page;
import top.sdaily.core.web.context.SessionUser;
import top.sdaily.core.web.exception.ErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 自动注入session级当前用户
 * Created by soya on 2016/12/2.
 */
public class CustomerArgumentResolver implements WebArgumentResolver {

    private ApplicationContext applicationContext;
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        if(applicationContext == null)
            applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());

        if(stringRedisTemplate == null) {
            stringRedisTemplate = (StringRedisTemplate) applicationContext.getBean("stringRedisTemplate");
        }

        if(methodParameter.getParameterType() != null
                && methodParameter.getParameterType().equals(SessionUser.class)){

            String token = request.getHeader("Access-Token");

            if(token == null ) return UNRESOLVED;

            SessionUser sessionUser = JSON.parseObject(stringRedisTemplate.opsForValue().get(token),SessionUser.class);

            if(sessionUser == null ) return UNRESOLVED;

            return sessionUser;
        }

        if(methodParameter.getParameterType() != null
                && methodParameter.getParameterType().equals(Page.class)){
            Page page = new Page();
            int pageNo = Integer.getInteger(request.getParameter("pageNo"));
            Integer pageSize  = Integer.getInteger(request.getParameter("pageSize"),10);
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            return page;
        }
        return UNRESOLVED;
    }

}
