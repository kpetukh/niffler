package niffler.test.grpc;

import guru.qa.grpc.niffler.grpc.NifflerCurrencyServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.qameta.allure.grpc.AllureGrpc;
import niffler.config.App;
import org.junit.jupiter.api.Test;

public abstract class BaseGRPCTest {

    private static final Channel CHANNEL = ManagedChannelBuilder
            .forAddress(App.CURRENCY_URI, App.CURRENCY_PORT)
            .intercept(new AllureGrpc())
            .usePlaintext()
            .build();

    protected NifflerCurrencyServiceGrpc.NifflerCurrencyServiceBlockingStub nifflerCurrencyBlockingStub =
            NifflerCurrencyServiceGrpc.newBlockingStub(CHANNEL);

}
