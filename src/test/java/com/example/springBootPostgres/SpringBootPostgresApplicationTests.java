package com.example.springBootPostgres;


import com.example.springBootPostgres.api.Specification;
import com.example.springBootPostgres.api.dto.UserDto;
import com.example.springBootPostgres.entity.User;
import com.example.springBootPostgres.repository.UserRepository;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest

class SpringBootPostgresApplicationTests {
	private final static String URL = "http://localhost:8080";
	private final static String APIUSERS ="/users/";

	@Autowired
	UserRepository userRepository;

	@Test
	public void createEmployeeTest() {
		Specification.installSpecification(Specification.requestSpec(URL),Specification.responceSpecOk200());
		UserDto userDTO = new UserDto(4, "Sibilla", "test@test.ru");
		Response response = given()
				.body(userDTO)
				.when()
				.post(APIUSERS).
				then().log().all()
				.extract().response();
				List<User> users = userRepository.findAll();
		        Assertions.assertThat(users.size()).isGreaterThan(0);




	}
//	@Test
//    public void checkIDsTest(){
//		List<User> users = userRepository.findAll();
//		Assertions.assertThat(users.size()).isGreaterThan(0);
//
//	}



}