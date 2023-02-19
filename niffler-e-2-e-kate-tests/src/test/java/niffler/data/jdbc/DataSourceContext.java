package niffler.data.jdbc;

import niffler.config.App;
import niffler.data.DataBase;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.Map;
import java.util.HashMap;
import javax.sql.DataSource;

public enum  DataSourceContext {
    INSTANCE;

    private Map<DataBase, DataSource> dsContext = new HashMap<>();

    public synchronized DataSource getDataSource(DataBase dataBase) {
        if (dsContext.get(dataBase) == null) {
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setUser(App.CONFIG.dataBaseUser());
            dataSource.setPassword(App.CONFIG.dataBasePassword());
            dataSource.setURL(dataBase.url);
            dsContext.put(dataBase, dataSource);
        }
        return dsContext.get(dataBase);
    }
}
