package top.sdaily.core.web.spring.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
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

    @Autowired
    private RedisTemplate<String, SessionUser> redisTemplate;

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception {
        if(methodParameter.getParameterType() != null
                && methodParameter.getParameterType().equals(SessionUser.class)){
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            String token = request.getHeader("Access-Token");

            if(token == null ) throw new ErrorException("user does not exist");

            SessionUser sessionUser = redisTemplate.opsForValue().get(token);

            if(token == null ) throw new ErrorException("invalid token");

            return sessionUser;
        }

        if(methodParameter.getParameterType() != null
                && methodParameter.getParameterType().equals(Page.class)){
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            Page page = new Page();
            int pageNo = (Integer) request.getAttribute("pageNo");
            Optional<Integer> pageSize  = Optional.ofNullable((Integer) request.getAttribute("pageSize"));
            page.setPageNo(pageNo);
            page.setPageSize(pageSize.orElse(10));
            return page;
        }
        return null;
    }

}
