package top.sdaily;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by soya on 2016/11/11.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigClientApplication.class).web(true).run(args);
    }

}
