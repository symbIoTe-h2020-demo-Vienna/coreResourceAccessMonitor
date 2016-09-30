package eu.h2020.symbiote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.h2020.symbiote.messaging.MessagingSubscriptions;


/**
 * Created by mateuszl on 22.09.2016.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CoreResourceAccessMonitorApplication {

	private static Log log = LogFactory.getLog(CoreResourceAccessMonitorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CoreResourceAccessMonitorApplication.class, args);

        try {
            MessagingSubscriptions.subscribeForCRAM();
        } catch (Exception e) {
            log.error("Error occured during subscribing from Core Resource Access Monitor", e);
        }
	}

	@Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
