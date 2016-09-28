package eu.h2020.symbiote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * Created by mateuszl on 22.09.2016.
 */
//@EnableDiscoveryClient
@SpringBootApplication
public class CoreResourceAccessMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreResourceAccessMonitorApplication.class, args);
	}

	@Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
