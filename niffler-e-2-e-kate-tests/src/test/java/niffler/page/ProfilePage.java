package niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    public SelenideElement currencySelector() {
        return $("span[id^='react-select']").parent();
    }
}
