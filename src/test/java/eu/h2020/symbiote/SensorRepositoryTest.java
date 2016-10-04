// package eu.h2020.symbiote;


// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import org.junit.Test;
// import org.junit.Before;

// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.context.annotation.Bean;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import java.net.URL;
// import java.util.List;
// import org.json.simple.JSONObject;

// import com.rabbitmq.client.Envelope;
// import com.rabbitmq.client.AMQP;
// import com.rabbitmq.client.Channel;
// import com.rabbitmq.client.Connection;
// import com.rabbitmq.client.ConnectionFactory;

// import java.io.IOException;
// import java.util.concurrent.TimeoutException;

// import eu.h2020.symbiote.repository.SensorRepository;
// import eu.h2020.symbiote.model.*;
// import eu.h2020.symbiote.messaging.ResourceCreatedConsumer;

// import static org.junit.Assert.assertEquals;


// @RunWith(SpringJUnit4ClassRunner.class)
// //@ContextConfiguration(classes={CoreResourceAccessMonitorApplication.class})
// public class SensorRepositoryTest {


// 	private static final Logger LOG = LoggerFactory
// 						.getLogger(SensorRepositoryTest.class);

// 	//@MockBean
// 	private ResourceCreatedConsumer resourceCreatedConsumer;

// 	// @Autowired
// 	// private SensorRepository sensorRepo;

// 	@Before
// 	public void setup() throws IOException, TimeoutException {
// 	    ConnectionFactory factory = new ConnectionFactory();
// 	    factory.setHost("localhost");
// 	    Connection connection = factory.newConnection();
// 	    Channel channel = connection.createChannel();
//         resourceCreatedConsumer = new ResourceCreatedConsumer(channel);
// 	}


// 	@Test
// 	public void testRepo() throws Exception {

// 		Location location = new Location("mock_id", "mock_name", "mock_description", 0.1, 0.1, 0.1);
// 		URL url = new URL("http://www.symbIoTe.com");
// 		Platform platform = new Platform ("mock_id", "mock_owner", "mock_name", "mock_type", url);
// 		Sensor sensor = new Sensor("1", "Sensor1", "OpenIoT", "Temperature Sensor", location, "temp", platform);
// 		JSONObject sensorObj = new JSONObject();
// 		JSONObject platformObj = new JSONObject();
// 		JSONObject locationObj = new JSONObject();

// 		locationObj.put("id", "mock_id");
// 		locationObj.put("name", "mock_name");
// 		locationObj.put("description", "mock_description");
// 		locationObj.put("longitude", 0.1);
// 		locationObj.put("latitude", 0.1);
// 		locationObj.put("altitude", 0.1);

// 		platformObj.put("id", "mock_id");
// 		platformObj.put("owner", "mock_owner");
// 		platformObj.put("name", "mock_name");
// 		platformObj.put("type", "mock_type");
// 		platformObj.put("resourceAccessProxyUrl", url.toString());

// 		sensorObj.put("id", "1");
// 		sensorObj.put("name","Sensor1");
// 		sensorObj.put("owner","OpenIoT");
// 		sensorObj.put("description","Temperature Sensor");
// 		sensorObj.put("location", locationObj);
// 		sensorObj.put("observedProperty","temp");
// 		sensorObj.put("platform", platformObj);

		

// 		// resourceCreatedConsumer.handleDelivery("consumerTag", new Envelope(1, true, "hi", "hello"), new AMQP.BasicProperties(), sensorObj.toString().getBytes("UTF-8"));
// 		// Sensor result = sensorRepo.findOne("1");
// 		// assertEquals(result.getName(), "Sensor1");
// 	}

// 	// @Bean
//  //    SensorRepository sensorRepository() {
//  //        return new SensorRepository();
//  //    }

// 	// @Bean
// 	// ResourceCreatedConsumer resourceCreatedConsumer() throws IOException, TimeoutException {
// 	// 	ConnectionFactory factory = new ConnectionFactory();
//  //        factory.setHost("127.0.0.1");
//  //        Connection connection = factory.newConnection();
//  //        Channel channel = connection.createChannel();
//  //        return new ResourceCreatedConsumer(channel);
// 	// }

// }
