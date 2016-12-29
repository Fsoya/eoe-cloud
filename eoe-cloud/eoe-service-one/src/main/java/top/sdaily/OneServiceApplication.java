package top.sdaily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by soya on 2016/11/5.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OneServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(OneServiceApplication.class).web(true).run(args);
    }
}
