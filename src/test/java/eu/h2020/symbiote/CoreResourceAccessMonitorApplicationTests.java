package eu.h2020.symbiote;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.web.client.MockRestServiceServer;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.client.match.MockRestRequestMatchers .method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CoreResourceAccessMonitorApplicationTests {


	private static final Logger LOG = LoggerFactory
						.getLogger(CoreResourceAccessMonitorApplicationTests.class);
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	@Autowired
	private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

	private MockRestServiceServer mockServer;

    private MockMvc mockMvc;

	// Execute the Setup method before the test.
	@Before
	public void setUp() {

		mockMvc = webAppContextSetup(webApplicationContext).build();
		// create a mock Server instance for RestTemplate
		mockServer = MockRestServiceServer.createServer(restTemplate);

	}

	@Test
	//@DisplayName("Testing Access Controller's GET method")
	public void testGet() throws Exception {

		mockServer.expect(requestTo("http://symbIoTe.com/urls"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess("{ \"1\" : \"2\", \"2\" : \"3\" }", MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/access/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.1", is("11")))
                .andExpect(jsonPath("$.2", is("12")));

		mockServer.verify();

	}

	@Test
	//@DisplayName("Testing Access Controller's GET method")
	public void testPost() throws Exception {

		mockServer.expect(requestTo("http://symbIoTe.com/urls"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess("{ \"1\" : \"2\", \"2\" : \"3\" }", MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/access")
        		.content("{ \"1\" : \"200\", \"2\" : \"300\" }")
        		.contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.1", is("11")))
                .andExpect(jsonPath("$.2", is("12")));

		mockServer.verify();

	}}
