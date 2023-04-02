
package niffler.config;

import org.aeonbits.owner.ConfigFactory;

public class App {
    public static final AppConfig CONFIG = ConfigFactory.create(AppConfig.class, System.getProperties());
    public static final String FRONTEND_URL = CONFIG.frontendUrl();
    public static final String SPEND_URI = CONFIG.spendUri();
    public static final String USER_URI = CONFIG.userUri();
    public static final String CURRENCY_URI = CONFIG.currencyUri();
    public static final int CURRENCY_PORT = CONFIG.currencyPort();
}
