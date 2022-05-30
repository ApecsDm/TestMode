package ru.netology.util;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private DataGenerator() {
    }

    private final static Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void sendRequest(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto(user.getLogin(), user.getPassword(), user.getStatus()))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static class Registration {
        private Registration() {
        }

        public static String GenerateLogin() {
            return faker.name().firstName().toLowerCase(Locale.ROOT);

        }

        public static String GeneratePassword() {
            return faker.internet().password();
        }

        public static RegistrationDto getUser(String status) {
            RegistrationDto user = new RegistrationDto(GenerateLogin(), GeneratePassword(), status);
            sendRequest(user);
            return user;
        }

        public static RegistrationDto registeredUser(String status) {
            RegistrationDto registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }
}
