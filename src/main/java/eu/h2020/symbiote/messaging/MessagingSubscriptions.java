package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mael on 07/09/2016.
 */
public class MessagingSubscriptions {

    private static String SENSOR_REGISTERED_QUEUE = "SensorRegistered";

    private static Log log = LogFactory.getLog(MessagingSubscriptions.class);

    public static void subscribeForCRAM() throws IOException, TimeoutException {
        subscribeSensorRegistered(SENSOR_REGISTERED_QUEUE);
    }

    public static void subscribeSensorRegistered( String queueName ) throws IOException, TimeoutException {
        Channel channel = getChannel(queueName);
        SensorRegisteredConsumer consumer = new SensorRegisteredConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
    }

    private static Channel getChannel( String queueName ) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        log.info("Receiver waiting for messages in queue " + queueName + " ....");
        return channel;
    }
}
