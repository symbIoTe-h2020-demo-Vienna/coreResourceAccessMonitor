package eu.h2020.symbiote;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.context.web.WebAppConfiguration;

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
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CoreResourceAccessMonitorApplicationTests {

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
	@BeforeEach
	public void setUp() {

		mockMvc = webAppContextSetup(webApplicationContext).build();
		// create a mock Server instance for RestTemplate
		mockServer = MockRestServiceServer.createServer(restTemplate);

	}

	@Test
	@DisplayName("Testing Access Controller's GET method")
	public void testGet() throws Exception {

		mockServer.expect(requestTo("http://localhost:8080/urls/"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));


        mockMvc.perform(get("http://localhost:8080/urls/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));

		mockServer.verify();

	}
}
