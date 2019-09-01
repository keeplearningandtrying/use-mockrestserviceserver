package com.wei.mock_server_test;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestTemplate;

public class Service {
	
	private RestTemplate restTemplate;
	
	private OAuth2RestTemplate oauth2RestTemplate;
	
	public Service(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Service(OAuth2RestTemplate oauth2RestTemplate) {
		this.oauth2RestTemplate = oauth2RestTemplate;
	}
	
	public Employee callServiceUsingRestTemplate(String uri) {
	    Employee result = restTemplate.getForObject(uri, Employee.class);
	    return result;
	}

	public Employee callServiceUsingOAuth2RestTemplate(String uri) {
	    Employee result = oauth2RestTemplate.getForObject(uri, Employee.class);
	    return result;
	}
	
}
