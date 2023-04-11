package niffler.test.grpc;

import com.google.protobuf.Empty;
import guru.qa.grpc.niffler.grpc.CalculateRequest;
import guru.qa.grpc.niffler.grpc.Currency;
import guru.qa.grpc.niffler.grpc.CurrencyValues;
import niffler.data.dao.CurrencyRepository;
import niffler.data.dao.PostgresHibernateCurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static niffler.model.Currency.EUR;
import static niffler.model.Currency.KZT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class NifflerCurrencyTest extends BaseGRPCTest {

    CurrencyRepository currencyRepository = new PostgresHibernateCurrencyRepository();

    @Test
    void getAllCurrenciesTest() {
        List<Currency> actualCurrencies = nifflerCurrencyBlockingStub.getAllCurrencies(Empty.getDefaultInstance())
                .getAllCurrenciesList();

        List<CurrencyValues> expectedCurrencyValues = Arrays.stream(niffler.model.Currency.values())
                .map(currency -> CurrencyValues.valueOf(currency.name()))
                .toList();

        List<CurrencyValues> actualCurrencyValues = actualCurrencies.stream()
                .map(Currency::getCurrency)
                .toList();

        assertThat(actualCurrencyValues, containsInAnyOrder(expectedCurrencyValues.toArray()));
    }

    @EnumSource(niffler.model.Currency.class)
    @ParameterizedTest(name = "{0}")
    void convertToKztTest(niffler.model.Currency currency) {
        double spend = new Random().nextDouble(100.0, 10_000.0);

        double expectedAmount = convertCurrencySpend(spend, currency, KZT);

        double actualAmount = nifflerCurrencyBlockingStub.calculateRate(
                        CalculateRequest.newBuilder()
                                .setSpendCurrency(CurrencyValues.valueOf(currency.name()))
                                .setDesiredCurrency(CurrencyValues.KZT)
                                .setAmount(spend)
                                .build()
                )
                .getCalculatedAmount();

        assertThat(actualAmount, equalTo(expectedAmount));
    }

    @EnumSource(niffler.model.Currency.class)
    @ParameterizedTest(name = "{0}")
    void convertToEurTest(niffler.model.Currency currency) {
        double spend = new Random().nextDouble(100.0, 10_000.0);

        double expectedAmount = convertCurrencySpend(spend, currency, EUR);

        double actualAmount = nifflerCurrencyBlockingStub.calculateRate(
                        CalculateRequest.newBuilder()
                                .setSpendCurrency(CurrencyValues.valueOf(currency.name()))
                                .setDesiredCurrency(CurrencyValues.EUR)
                                .setAmount(spend)
                                .build()
                )
                .getCalculatedAmount();

        assertThat(actualAmount, equalTo(expectedAmount));
    }

    private double convertCurrencySpend(double amount, niffler.model.Currency spendCurrency, niffler.model.Currency desiredCurrency) {
        double spendCurrencyRate = currencyRepository.findByCurrency(spendCurrency.toString()).getCurrencyRate();
        double desiredCurrencyRate = currencyRepository.findByCurrency(desiredCurrency.toString()).getCurrencyRate();

        return BigDecimal.valueOf((amount * spendCurrencyRate) / desiredCurrencyRate).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
