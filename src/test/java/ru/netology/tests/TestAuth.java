package ru.netology.tests;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.pages.*;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class TestAuth {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    public static void cleanDatabase() {
        cleanDb();
    }

    @Test
    void shouldLoginIfEnteredCorrectData() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val verificationCode = getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.dashboardPageIsVisible();
    }

    @Test
    void shouldNotLoginIfEnteredInvalidLogin() {
        val loginPage = new LoginPage();
        val authInfo = getInvalidLogin();
        loginPage.authSteps(authInfo);
        loginPage.invalidAuth();
    }

    @Test
    void shouldNotLoginIfEnteredInvalidPassword() {
        val loginPage = new LoginPage();
        val authInfo = getInvalidPassword();
        loginPage.authSteps(authInfo);
        loginPage.invalidAuth();
    }

    @Test
    void shouldNotLoginIfEnteredInvalidCode() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val verificationCode = getInvalidVerificationCode();
        verificationPage.verifySteps(verificationCode);
        verificationPage.invalidVerify();
    }

    @Test
    void shouldNotLoginIfEnteredInvalidPasswordThreeTimes() {
        val loginPage = new LoginPage();
        val authInfo = getInvalidPassword();
        loginPage.authSteps(authInfo);
        loginPage.invalidAuth();
        loginPage.clearPasswordField();
        loginPage.sendInvalidPassword(authInfo.getPassword());
        loginPage.clearPasswordField();
        loginPage.sendInvalidPassword(authInfo.getPassword());
        loginPage.loginButtonShouldBeDisabled();
    }
}