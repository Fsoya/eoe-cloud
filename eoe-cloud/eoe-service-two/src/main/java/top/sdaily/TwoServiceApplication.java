package top.sdaily;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by soya on 2016/11/8.
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class TwoServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TwoServiceApplication.class).web(true).run(args);
    }

}
