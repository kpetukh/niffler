package niffler.test;

import niffler.pages.LoginPage;
import niffler.jupiter.AddSpendExtension;

import niffler.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.visible;

@ExtendWith(AddSpendExtension.class)
public class SpendsTableTest {
    @Test
    void spendsTableHistory() {
        LoginPage.login();
        MainPage.spendingTableBody().shouldBe(visible);
    }
}
