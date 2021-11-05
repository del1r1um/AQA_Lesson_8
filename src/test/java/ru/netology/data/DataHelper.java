package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor
public class DataHelper {

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidLogin() {
        Faker faker = new Faker();
        return new AuthInfo(faker.name().firstName(), "qwerty123");
    }

    public static AuthInfo getInvalidPassword() {
        Faker faker = new Faker();
        return new AuthInfo("vasya", faker.internet().password());
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static String getInvalidVerificationCode() {
        return "1100110";
    }
}