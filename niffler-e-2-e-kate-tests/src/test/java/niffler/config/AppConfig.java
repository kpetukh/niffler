package niffler.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/app.properties")
public interface AppConfig extends Config {
    @Key("application.frontend.url")
    String frontendUrl();

    @Key("application.spend.uri")
    String spendUri();

    @Key("database.user")
    String dataBaseUser();

    @Key("database.password")
    String dataBasePassword();

    @Key("database.userdata.url")
    String dataBaseUserdataUrl();

    @Key("database.auth.url")
    String dataBaseAuthUrl();

    @Key("database.spend.url")
    String dataBaseSpendUrl();

    @Key("database.currency.url")
    String dataBaseCurrencyUrl();
}
