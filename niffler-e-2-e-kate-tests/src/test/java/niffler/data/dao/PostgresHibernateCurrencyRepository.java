package niffler.data.dao;

import niffler.data.DataBase;
import niffler.data.entity.CurrencyEntity;
import niffler.data.jpa.EmfContext;
import niffler.data.jpa.JpaService;

public class PostgresHibernateCurrencyRepository extends JpaService implements CurrencyRepository {
    public PostgresHibernateCurrencyRepository() {
        super(EmfContext.INSTANCE.getEmf(DataBase.CURRENCY).createEntityManager());
    }

    @Override
    public CurrencyEntity findByCurrency(String currency) {
     return em.createQuery("select c from CurrencyEntity c where c.currency=:currency", CurrencyEntity.class)
        .setParameter("currency", currency)
        .getSingleResult();
    }
}
