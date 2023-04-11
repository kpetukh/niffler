package niffler.data;

import niffler.config.App;

public enum DataBase {
    USERDATA(App.CONFIG.dataBaseUserdataUrl()),
    AUTH(App.CONFIG.dataBaseAuthUrl()),
    SPEND(App.CONFIG.dataBaseSpendUrl()),
    CURRENCY(App.CONFIG.dataBaseCurrencyUrl());
    public final String url;

    DataBase(String url) {
        this.url = url;
    }
}
