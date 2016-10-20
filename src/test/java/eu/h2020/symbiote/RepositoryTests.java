package eu.h2020.symbiote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

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
@SpringBootTest({"eureka.client.enabled=false"})
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

		URL plarformUrl = new URL("http://www.symbIoTe.com");
		JSONObject platformObj = new JSONObject();

		platformObj.put("id", "55");
		platformObj.put("owner", "mock_owner");
		platformObj.put("name", "platform1");
		platformObj.put("type", "mock_type");
		platformObj.put("resourceAccessProxyUrl", plarformUrl.toString());


		platformCreatedConsumer.handleDelivery("consumerTag", new Envelope(1, true, "", ""), new AMQP.BasicProperties(), platformObj.toString().getBytes("UTF-8"));
		Platform result = platformRepo.findOne("55");
		assertEquals(result.getName(), "platform1");
	}

	@Test
	public void testSensorRepository() throws Exception {

  
		URL plarformUrl = new URL("http://www.symbIoTe.com");
		URL sensorUrl = new URL("http://www.symbIoTe.com/sensor1");
		JSONObject sensorObj = new JSONObject();
		JSONObject platformObj = new JSONObject();
		JSONObject locationObj = new JSONObject();
		JSONObject pointObj = new JSONObject();
		JSONArray observedProperties = new JSONArray();

		pointObj.put("x", 0.1);
		pointObj.put("y", 0.1);

		locationObj.put("id", "mock_id");
		locationObj.put("name", "mock_name");
		locationObj.put("description", "mock_description");
		locationObj.put("point", pointObj);
		locationObj.put("altitude", 0.1);

		platformObj.put("id", "mock_id");
		platformObj.put("owner", "mock_owner");
		platformObj.put("name", "mock_name");
		platformObj.put("type", "mock_type");
		platformObj.put("resourceAccessProxyUrl", plarformUrl.toString());

		observedProperties.add("temp");
		observedProperties.add("air");

		sensorObj.put("id", "1");
		sensorObj.put("name","Sensor1");
		sensorObj.put("owner","OpenIoT");
		sensorObj.put("description","Temperature Sensor");
		sensorObj.put("location", locationObj);
		sensorObj.put("observedProperty",observedProperties);
		sensorObj.put("platform", platformObj);
		sensorObj.put("resourceURL", sensorUrl.toString());

		

		resourceCreatedConsumer.handleDelivery("consumerTag", new Envelope(1, true, "", ""), new AMQP.BasicProperties(), sensorObj.toString().getBytes("UTF-8"));
		Sensor result = sensorRepo.findOne("1");
		assertEquals(result.getName(), "Sensor1");
	}

}
