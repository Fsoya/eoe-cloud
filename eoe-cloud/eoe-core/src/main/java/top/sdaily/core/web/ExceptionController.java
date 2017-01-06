package top.sdaily.core.web;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import top.sdaily.core.web.exception.FailedException;
import top.sdaily.core.web.exception.InvalidTokenException;

/**
 * Created by soya on 2016/10/29.
 */
@ControllerAdvice
public class ExceptionController {

    private final Logger log = Logger.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnBody exceptionHandler(WebRequest request, Exception e){
        if(e instanceof FailedException){
            return ReturnBody.failed().setMsg(e.getMessage());
        }else if(e instanceof InvalidTokenException){
            return ReturnBody.invalidToken();
        }else {
            log.error("系统出现异常:", e);
            return ReturnBody.error().setMsg(e.getMessage());
        }
    }

}
