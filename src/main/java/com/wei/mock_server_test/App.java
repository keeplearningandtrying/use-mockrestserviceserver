package com.wei.mock_server_test;

import org.springframework.web.client.RestTemplate;

public class App {
	
	public static void main(String[] args) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		Service service = new Service(restTemplate);
		
		Employee e = service.callServiceUsingRestTemplate("http://localhost:8080/employee/1");
		
		System.out.println(e);
	}

}
