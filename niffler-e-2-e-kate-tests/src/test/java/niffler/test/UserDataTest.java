package niffler.test;

import niffler.page.MainPage;
import niffler.page.LoginPage;
import niffler.page.ProfilePage;
import niffler.data.dao.UsersDAO;
import niffler.data.dao.PostgresSpringJdbcUsersDAO;
import niffler.data.entity.UsersEntity;
import niffler.jupiter.UserEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.text;
import static niffler.model.Currency.EUR;

public class UserDataTest {

    private UsersDAO usersDAO = new PostgresSpringJdbcUsersDAO();

    @UserEntity(username = "dima", currency = EUR)
    UsersEntity dima;
    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();
    ProfilePage profilePage = new ProfilePage();

    @BeforeEach
    void addUserDataBeforeTest() {
        loginPage.login("dima", "12345");
    }

    @Test
    void checkCurrencyTest() {
        mainPage.usernameLink().click();
        profilePage.currencySelector().should(text(dima.getCurrency()));
    }
}
