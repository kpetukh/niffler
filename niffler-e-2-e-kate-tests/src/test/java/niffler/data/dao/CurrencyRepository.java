package niffler.data.dao;

import niffler.data.entity.CurrencyEntity;

public interface CurrencyRepository extends DAO {

    CurrencyEntity findByCurrency(String currency);
}
