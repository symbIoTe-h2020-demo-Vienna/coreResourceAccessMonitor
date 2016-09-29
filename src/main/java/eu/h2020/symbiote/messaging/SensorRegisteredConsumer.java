package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.Channel;
import eu.h2020.symbiote.model.Sensor;
// import eu.h2020.symbiote.search.SearchStorage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Mael on 07/09/2016.
 */
public class SensorRegisteredConsumer extends SymbioteMessageConsumer<Sensor>{

    private static Log log = LogFactory.getLog(SensorRegisteredConsumer.class);

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public SensorRegisteredConsumer(Channel channel) {
        super(channel);
    }

    @Override
    protected void handleEventObject(Sensor deliveredObject) {
        log.info("Search received message about created platform " + deliveredObject.getId());
        // SearchStorage.getInstance().registerPlatform(deliveredObject);
    }
}
