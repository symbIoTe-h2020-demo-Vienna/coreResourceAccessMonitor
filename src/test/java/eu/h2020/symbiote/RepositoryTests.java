package eu.h2020.symbiote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.boot.test.IntegrationTest;

import java.net.URL;
import org.json.simple.JSONObject;

import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import eu.h2020.symbiote.repository.SensorRepository;
import eu.h2020.symbiote.repository.PlatformRepository;
import eu.h2020.symbiote.model.*;
import eu.h2020.symbiote.messaging.ResourceCreatedConsumer;
import eu.h2020.symbiote.messaging.PlatformCreatedConsumer;

import static org.junit.Assert.assertEquals;

/** 
 * This file tests the PlatformRepository and SensorRepository
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={CoreResourceAccessMonitorApplication.class})
@IntegrationTest({"eureka.client.enabled=false"})
public class RepositoryTests {


	private static final Logger LOG = LoggerFactory
						.getLogger(RepositoryTests.class);

	private ResourceCreatedConsumer resourceCreatedConsumer;

	private PlatformCreatedConsumer platformCreatedConsumer;

	@Autowired
	private SensorRepository sensorRepo;

	@Autowired
	private PlatformRepository platformRepo;


	@Before
	public void setup() throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
        resourceCreatedConsumer = new ResourceCreatedConsumer(channel);
        platformCreatedConsumer = new PlatformCreatedConsumer(channel);
	}


	@Test
	public void testPlatformRepository() throws Exception {

		Location location = new Location("mock_id", "mock_name", "mock_description", 0.1, 0.1, 0.1);
		URL url = new URL("http://www.symbIoTe.com");
		Platform platform = new Platform ("mock_id", "mock_owner", "mock_name", "mock_type", url);
		JSONObject platformObj = new JSONObject();

		platformObj.put("id", "55");
		platformObj.put("owner", "mock_owner");
		platformObj.put("name", "platform1");
		platformObj.put("type", "mock_type");
		platformObj.put("resourceAccessProxyUrl", url.toString());


		platformCreatedConsumer.handleDelivery("consumerTag", new Envelope(1, true, "", ""), new AMQP.BasicProperties(), platformObj.toString().getBytes("UTF-8"));
		Platform result = platformRepo.findOne("55");
		assertEquals(result.getName(), "platform1");
	}

	@Test
	public void testSensorRepository() throws Exception {

		Location location = new Location("mock_id", "mock_name", "mock_description", 0.1, 0.1, 0.1);
		URL url = new URL("http://www.symbIoTe.com");
		Platform platform = new Platform ("mock_id", "mock_owner", "mock_name", "mock_type", url);
		Sensor sensor = new Sensor("1", "Sensor1", "OpenIoT", "Temperature Sensor", location, "temp", platform);
		JSONObject sensorObj = new JSONObject();
		JSONObject platformObj = new JSONObject();
		JSONObject locationObj = new JSONObject();

		locationObj.put("id", "mock_id");
		locationObj.put("name", "mock_name");
		locationObj.put("description", "mock_description");
		locationObj.put("longitude", 0.1);
		locationObj.put("latitude", 0.1);
		locationObj.put("altitude", 0.1);

		platformObj.put("id", "mock_id");
		platformObj.put("owner", "mock_owner");
		platformObj.put("name", "mock_name");
		platformObj.put("type", "mock_type");
		platformObj.put("resourceAccessProxyUrl", url.toString());

		sensorObj.put("id", "1");
		sensorObj.put("name","Sensor1");
		sensorObj.put("owner","OpenIoT");
		sensorObj.put("description","Temperature Sensor");
		sensorObj.put("location", locationObj);
		sensorObj.put("observedProperty","temp");
		sensorObj.put("platform", platformObj);

		

		resourceCreatedConsumer.handleDelivery("consumerTag", new Envelope(1, true, "", ""), new AMQP.BasicProperties(), sensorObj.toString().getBytes("UTF-8"));
		Sensor result = sensorRepo.findOne("1");
		assertEquals(result.getName(), "Sensor1");
	}

}
