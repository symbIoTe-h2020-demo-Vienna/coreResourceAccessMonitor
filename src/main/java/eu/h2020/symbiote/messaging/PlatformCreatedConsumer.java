package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.repository.PlatformRepository;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by Mael on 07/09/2016.
 */
public class PlatformCreatedConsumer extends SymbioteMessageConsumer<Platform> {

    private static Log log = LogFactory.getLog(PlatformCreatedConsumer.class);

    @Autowired
    private PlatformRepository platformRepo;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */

    @Autowired
    public PlatformCreatedConsumer(Channel channel) {
        super(channel);
    }


    /**
     * Method implementation used for actions with object passed in delivered message (Platform in JSON in this case)
     *
     * * @param deliveredObject
     */
    @Override
    protected void handleEventObject(Platform deliveredObject) {
        System.out.println("CRAM received message about created platform with id: " + deliveredObject.getId());
        
        //save (deliveredObject) in database
        //platformRepo.save(deliveredObject);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(deliveredObject, headers);

        ResponseEntity<String> out = restTemplate.exchange("http://localhost:8202/cram_api/platform", HttpMethod.POST, entity,
             String.class);    
    }
}