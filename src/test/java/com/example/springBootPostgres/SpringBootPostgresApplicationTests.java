package com.example.springBootPostgres;


import com.example.springBootPostgres.api.Specification;
import com.example.springBootPostgres.api.dto.UserDto;
import com.example.springBootPostgres.entity.User;
import com.example.springBootPostgres.repository.UserRepository;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest

class SpringBootPostgresApplicationTests {
	private final static String URL = "http://localhost:8080";
	private final static String API_USERS ="/users/";
	private final static String API_USERS_ID = API_USERS+"1";

	@Autowired
	UserRepository userRepository;

	@Test
	public void createEmployeeTest() {
		Specification.installSpecification(Specification.requestSpec(URL),Specification.responceSpecOk200());
		UserDto userDTO = new UserDto(1, "Anna", "Anna@test.ru");
		Response response = given()
				.body(userDTO)
				.when()
				.post(API_USERS).
				then().log().all()
				.extract().response();
				List<User> users = userRepository.findAll();
		        Assertions.assertThat(users.size()).isGreaterThan(0);

	}

	@Test
	public void findUserById(){
		Specification.installSpecification(Specification.requestSpec(URL),Specification.responceSpecOk200());
		Response response = given()
				.with()
				.get(API_USERS_ID)
				.then().log().all()
				.extract().response();
		User user = userRepository.findByName("Anna").get();
		Assertions.assertThat(user.getName()).isEqualTo("Anna");
		Assertions.assertThat(user.getEmail()).endsWith("@test.ru");
	}


	@Test
	public void updateUserTest(){
		Specification.installSpecification(Specification.requestSpec(URL),Specification.responceSpecOk200());
		UserDto userDTO = new UserDto(1, "Elena", "Elena@test.ru");
		Response response = given()
				.body(userDTO)
				.when()
				.put(API_USERS).
				then().log().all()
				.extract().response();
		User user = userRepository.findByName("Anna").get();
		Assertions.assertThat(user.getName()).isEqualTo("Elena");
		Assertions.assertThat(user.getEmail()).startsWith("Elena");
	}


	@Test
	public void deleteUser(){
		Specification.installSpecification(Specification.requestSpec(URL),Specification.responceSpecOk200());
		Response response = given()
				.with()
				.delete(API_USERS_ID)
				.then().log().all()
				.extract().response();
		User user = userRepository.findByName("Elena").get();
		Assertions.assertThat(user.getName()).isNull();

	}


	@Test
	public void cleanDB(){
		userRepository.deleteAll();
	}


}