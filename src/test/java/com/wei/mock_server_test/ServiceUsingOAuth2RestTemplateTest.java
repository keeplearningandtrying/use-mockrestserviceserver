package com.wei.mock_server_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.test.web.client.MockRestServiceServer;

public class ServiceUsingOAuth2RestTemplateTest {

	private OAuth2RestTemplate oauth2RestTemplate;
	private Service service;
	private MockRestServiceServer mockServer;
	private String uri;

	@Before
	public void setUp() {
		uri = "http://localhost:8080/employee/1";
		BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
		resource.setId("resource");
		oauth2RestTemplate = new OAuth2RestTemplate(resource);
		service = new Service(oauth2RestTemplate);
		mockServer = MockRestServiceServer.createServer(oauth2RestTemplate);
	}

	@Test
	public void testCallServiceUsingOAuth2RestTemplate1() {
		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken("accesstoken");
		oauth2RestTemplate.getOAuth2ClientContext().setAccessToken(token);

		String json = "{\"id\":\"m123\",\"employee_name\":\"Wei\",\"employee_salary\":\"3456\",\"employee_age\":\"20\",\"profile_image\":\"image\"}";
		mockServer.expect(requestTo(uri)).andExpect(method(HttpMethod.GET)).andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
		Employee employee = service.callServiceUsingOAuth2RestTemplate(uri);
		assertThat(employee.getId()).isEqualTo("m123");
	}

	@Test(expected=OAuth2AccessDeniedException.class)
	public void testCallServiceUsingOAuth2RestTemplate2() {
		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken("accesstoken");
		token.setExpiration(new Date(System.currentTimeMillis() - 10000));
		oauth2RestTemplate.getOAuth2ClientContext().setAccessToken(token);

		String json = "{\"id\":\"m123\",\"employee_name\":\"Wei\",\"employee_salary\":\"3456\",\"employee_age\":\"20\",\"profile_image\":\"image\"}";
		mockServer.expect(requestTo(uri)).andExpect(method(HttpMethod.GET)).andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
		Employee employee = service.callServiceUsingOAuth2RestTemplate(uri);
		assertThat(employee.getId()).isEqualTo("m123");
	}

	@Test(expected=OAuth2AccessDeniedException.class)
	public void testCallServiceUsingOAuth2RestTemplate3() {
		DefaultOAuth2AccessToken token = null; 
		oauth2RestTemplate.getOAuth2ClientContext().setAccessToken(token);

		String json = "{\"id\":\"m123\",\"employee_name\":\"Wei\",\"employee_salary\":\"3456\",\"employee_age\":\"20\",\"profile_image\":\"image\"}";
		mockServer.expect(requestTo(uri)).andExpect(method(HttpMethod.GET)).andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
		Employee employee = service.callServiceUsingOAuth2RestTemplate(uri);
		assertThat(employee.getId()).isEqualTo("m123");
	}

}
