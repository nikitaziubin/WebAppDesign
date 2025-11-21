package com.example.WebApplicationDesign;

import com.example.WebApplicationDesign.config.SecurityConfig;
import com.example.WebApplicationDesign.Users.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(SecurityConfig.class)
class WebApplicationDesignApplicationTests {

	@Resource
	TestRestTemplate restTemplate;

	@Value(value = "${local.server.port}")
	private int port;

	private String baseUrl() {
		return "http://localhost:" + port + "/api/users";
	}

	@Test
	void crd_user_test() {
		User user = new User();
		user.setId(0);
		user.setName("Nikita");
		user.setPassword("12345");
		user.setPhoneNumber("+37069437465");
		user.setEmail("nikziu1@ktu.lt");
		restTemplate.postForObject(baseUrl(), user, User.class);

		ResponseEntity<List<User>> response = restTemplate.exchange(
				baseUrl(),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<User>>() {}
		);
		List<User> existingUsers = response.getBody();
        assertFalse(existingUsers.isEmpty());

		int id = existingUsers.get(0).getId();
		restTemplate.delete(baseUrl() +"/" + id);
		response = restTemplate.exchange(
				baseUrl(),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<User>>() {}
		);
		assertEquals(0, response.getBody().size());
	}
}
