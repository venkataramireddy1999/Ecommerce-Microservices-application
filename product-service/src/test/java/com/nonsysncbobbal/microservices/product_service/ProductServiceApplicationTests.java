package com.nonsysncbobbal.microservices.product_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.mongodb.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.3.8");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String body = """
				{
					"name": "iPhone 14",
					"description": "iPhone 14 is the latest smartphone from Apple.",
					"price": 999.99
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/api/products")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iPhone 14"))
				.body("description", Matchers.equalTo("iPhone 14 is the latest smartphone from Apple."))
				.body("price", Matchers.equalTo(999.99f));
	}

}
