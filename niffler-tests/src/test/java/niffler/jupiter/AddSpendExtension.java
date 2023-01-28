package niffler.jupiter;

import niffler.api.SpendService;
import niffler.model.SpendModel;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.BeforeEachCallback;

import java.util.Date;

import static niffler.model.Currency.EUR;

public class AddSpendExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        SpendModel spend = SpendModel.builder()
                .spendDate(new Date())
                .category("Кафе")
                .currency(EUR)
                .amount(5.5)
                .description("Матча латте")
                .username("Kate").build();

        SpendService.addSpend(spend);
    }
}
