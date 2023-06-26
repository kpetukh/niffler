package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import io.qameta.allure.Epic;
import niffler.jupiter.annotation.ApiLogin;
import niffler.jupiter.annotation.Friends;
import niffler.jupiter.annotation.GenerateUser;
import niffler.jupiter.annotation.User;
import niffler.model.rest.UserJson;
import niffler.page.MainPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Epic("[WEB][niffler-frontend]: Друзья")
@DisplayName("[WEB][niffler-frontend]: Друзья")
public class FriendsTest extends BaseWebTest {

    @Test
    @AllureId("500018")
    @DisplayName("WEB: Пользователь должен видеть список своих друзей")
    @Tag("WEB")
    @ApiLogin(nifflerUser = @GenerateUser(friends = @Friends(count = 2)))
    void shouldViewExistingFriendsInTable(@User UserJson user) {
        user.getFriendsJsons().remove(0);
        user.getFriendsJsons().add(user);
        Selenide.open(MainPage.URL, MainPage.class)
                .getHeader()
                .toFriendsPage()
                .checkExistingFriends(user.getFriendsJsons());
    }

    //todo: add friend (send invitation)
    //todo delete friend from two pages
    //todo: see invitations on two pages
    //todo: accept invitation from two pages
    //todo: decline invitation from two pages

}
