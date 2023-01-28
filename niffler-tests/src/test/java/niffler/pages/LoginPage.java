package niffler.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class LoginPage {

    public static void login() {
        open("http://127.0.0.1:3000/");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue("Kate");
        $("input[name='password']").setValue("pass");
        $("button[type='submit']").click();
        $(".header__title").shouldBe(visible)
                .shouldHave(text("Niffler. The coin keeper."));
    }
}
