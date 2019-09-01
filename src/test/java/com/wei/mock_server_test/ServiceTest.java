package com.wei.mock_server_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

public class ServiceTest {
	
	private RestTemplate restTemplate;
	private Service service;
	private MockRestServiceServer mockServer;
	private String uri;
	
	@Before
	public void setUp() {
		uri = "http://localhost:8080/employee/1";
		restTemplate = new RestTemplate();
		service = new Service(restTemplate);
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}
	
	@Test
    public void testCallServiceUsingRestTemplate() {
		String json = "{\"id\":\"m123\",\"employee_name\":\"Wei\",\"employee_salary\":\"3456\",\"employee_age\":\"20\",\"profile_image\":\"image\"}";
        mockServer.expect(requestTo(uri)).andExpect(method(HttpMethod.GET)).andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        Employee employee = service.callServiceUsingRestTemplate(uri); 
        assertThat(employee.getId()).isEqualTo("m123");
    }

}
