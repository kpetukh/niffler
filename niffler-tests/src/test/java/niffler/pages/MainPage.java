package niffler.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    public static SelenideElement spendingTableBody() {
        return $(".table.spendings-table").$("tbody");
    }
}
