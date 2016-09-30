package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;

import eu.h2020.symbiote.model.Sensor;
import eu.h2020.symbiote.repository.SensorRepository;


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
    }
}