// package eu.h2020.symbiote;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import org.junit.Test;
// import org.junit.Before;

// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.boot.test.IntegrationTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.web.context.WebApplicationContext;

// import java.nio.charset.Charset;
// import java.net.URL;

// import eu.h2020.symbiote.repository.RepositoryManager;
// import eu.h2020.symbiote.model.*;

// import static org.hamcrest.Matchers.is;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


// @RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
// @IntegrationTest({"eureka.client.enabled=false"})
// public class CoreResourceAccessMonitorApplicationTests {


// 	private static final Logger LOG = LoggerFactory
// 						.getLogger(CoreResourceAccessMonitorApplicationTests.class);
//     private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//             MediaType.APPLICATION_JSON.getSubtype(),
//             Charset.forName("utf8"));

//     @Autowired
//     private WebApplicationContext webApplicationContext;

//     private MockMvc mockMvc;

// 	// Execute the Setup method before the test.
// 	@Before
// 	public void setUp() throws Exception {

// 		mockMvc = webAppContextSetup(webApplicationContext).build();

// 		Location location = new Location("location_id", "location_name", "location_description", 0.1, 0.1, 0.1);
// 		URL url = new URL("http://www.symbIoTe.com");
// 		Platform platform = new Platform ("platform_id", "platform_owner", "platform_name", "platform_type", url);
// 		Sensor sensor = new Sensor("sensor_id", "Sensor1", "OpenIoT", "Temperature Sensor", location, "temp", platform);
// 		Sensor sensor2 = new Sensor("sensor_id2", "Sensor2", "OpenIoT", "Temperature Sensor", location, "temp", platform);

// 		RepositoryManager.savePlatform(platform);
// 		RepositoryManager.saveSensor(sensor);
// 		RepositoryManager.saveSensor(sensor2);

// 	}

// 	@Test
// 	//@DisplayName("Testing Access Controller's GET method")
// 	public void testGet() throws Exception {

//         mockMvc.perform(get("/cram_api/resource_urls/sensor_id,sensor_id2"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(contentType))
//                 .andExpect(jsonPath("$.sensor_id", is("http://www.symbIoTe.com/platform_id/sensor_id")))
//                 .andExpect(jsonPath("$.sensor_id2", is("http://www.symbIoTe.com/platform_id/sensor_id2")));

// 	}

// 	@Test
// 	//@DisplayName("Testing Access Controller's GET method")
// 	public void testPost() throws Exception {

//         mockMvc.perform(post("/cram_api/resource_urls")
//         		.content("{ \"idList\" : [\"sensor_id\", \"sensor_id2\" ]}")
//         		.contentType(contentType))
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(contentType))
//                 .andExpect(jsonPath("$.sensor_id", is("http://www.symbIoTe.com/platform_id/sensor_id")))
//                 .andExpect(jsonPath("$.sensor_id2", is("http://www.symbIoTe.com/platform_id/sensor_id2")));

// 	}
// }
