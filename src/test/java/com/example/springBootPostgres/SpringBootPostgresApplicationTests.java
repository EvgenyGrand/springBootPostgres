package com.example.springBootPostgres;


import com.example.springBootPostgres.api.Specification;
import com.example.springBootPostgres.api.dto.UserDto;
import com.example.springBootPostgres.entity.User;
import com.example.springBootPostgres.repository.UserRepository;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringBootPostgresApplicationTests {
	private final static String URL = "http://localhost:8080";
	private final static String API_USERS ="/users/";
	private final static String API_USERS_ID = API_USERS+"1";

	@Autowired
	UserRepository userRepository;
    @Order(1)
	@DisplayName("Создание пользователя")
	@Test
	public void createEmployeeTest() {
		Specification.installSpecification(Specification.requestSpec(URL),Specification.responceSpecOk200());UserDto userDTO = new UserDto(1, "Anna", "Anna@test.ru");
		Response response = given()
				.body(userDTO)
				.when()
				.post(API_USERS).
				then().log().all()
				.extract().response();
				List<User> users = userRepository.findAll();
		        Assertions.assertThat(users.size()).isGreaterThan(0);

	}

	@Order(2)
	@DisplayName("поиск пользователя по id")
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

    @Order(3)
	@DisplayName("Редактирование пользователя")
	@Test
	public void updateUserTest(){
		Specification.installSpecification(Specification.requestSpec(URL),Specification.responceSpecOk200());
		UserDto userDTO = new UserDto(1, "Elena", "Elena@test.ru");
		Response response = given()
				.body(userDTO)
				.when()
				.put(API_USERS_ID).
				then().log().all()
				.extract().response();
		User user = userRepository.findByName("Elena").get();
		Assertions.assertThat(user.getName()).isEqualTo("Elena");
		Assertions.assertThat(user.getEmail()).startsWith("Elena");
	}

    @Order(4)
	@DisplayName("Удаление пользователя")
	@Test
	public void deleteUser(){
		Specification.installSpecification(Specification.requestSpec(URL),Specification.responceSpecOk200());
		Response response = given()
				.with()
				.delete(API_USERS_ID)
				.then().log().all()
				.extract().response();
		User user1 = null;
		Optional<User> findByName = userRepository.findByName("Elena");
		if(findByName.isPresent()){
			user1 = findByName.get();
		}
		Assertions.assertThat(user1).isNull();


	}
  @Order(5)
  @DisplayName("очистка БД")
	@Test
	public void cleanDB(){
		userRepository.deleteAll();


	}


}