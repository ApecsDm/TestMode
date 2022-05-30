package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.util.DataGenerator;
import ru.netology.util.RegistrationDto;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    void setUp() {
 //       Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldSuccessfulLoginIfUserIsRegisteredActive() {
        RegistrationDto validUser = DataGenerator.Registration.getUser("active");
        ;

        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(".heading").shouldBe(visible).shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void shouldGetErrorIfUserIsNotRegistered() {
        RegistrationDto blockedUser = DataGenerator.Registration.getUser("blocked");

        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(appear);
    }

    @Test
    void invalidLogin() {
        var registeredUser = DataGenerator.Registration.registeredUser("active");
        var wrongLogin = DataGenerator.Registration.GenerateLogin();

        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void invalidPassword() {
        var registeredUser = DataGenerator.Registration.registeredUser("active");
        var wrongPassword = DataGenerator.Registration.GeneratePassword();

        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

}
