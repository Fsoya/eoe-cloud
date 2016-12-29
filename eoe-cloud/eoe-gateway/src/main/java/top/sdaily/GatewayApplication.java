package top.sdaily;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import top.sdaily.filter.AccessFilter;

/**
 * Created by soya on 2016/11/8.
 */
@EnableZuulProxy
@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
}
