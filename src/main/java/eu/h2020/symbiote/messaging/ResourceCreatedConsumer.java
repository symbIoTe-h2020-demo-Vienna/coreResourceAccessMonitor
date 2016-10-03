package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;

import eu.h2020.symbiote.model.Sensor;
import eu.h2020.symbiote.repository.SensorRepository;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by Mael on 08/09/2016.
 */
public class ResourceCreatedConsumer extends SymbioteMessageConsumer<Sensor> {

    private static Log log = LogFactory.getLog(ResourceCreatedConsumer.class);

    @Autowired
    private SensorRepository sensorRepo;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public ResourceCreatedConsumer(Channel channel) {
        super(channel);
    }


    /**
     * Method implementation used for actions with object passed in delivered message (Sensor in JSON in this case)
     *
     * @param deliveredObject
     */
    @Override
    protected void handleEventObject(Sensor deliveredObject) {
        System.out.println("CRAM received message about created resource with id: " + deliveredObject.getId());
        
        //save (deliveredObject) in database
        //sensorRepo.save(deliveredObject);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(deliveredObject, headers);

        ResponseEntity<String> out = restTemplate.exchange("http://localhost:8300/cram_api/resource", HttpMethod.POST, entity,
             String.class);    
    }
}