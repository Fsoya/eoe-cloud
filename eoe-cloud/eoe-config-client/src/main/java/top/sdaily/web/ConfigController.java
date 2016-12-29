package top.sdaily.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by soya on 2016/11/11.
 */
@RefreshScope
@RestController
public class ConfigController {

    @Value("${from}")
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
    }
}
