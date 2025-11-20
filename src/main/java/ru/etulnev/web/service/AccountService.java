package ru.etulnev.web.service;

import ru.etulnev.web.dao.AccountDao;
import ru.etulnev.web.dao.AccountTypeDao;
import ru.etulnev.web.dao.domain.AccountDomain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class AccountService {
    private final AccountTypeDao accountTypeDao = new AccountTypeDao();
    private final AccountDao accountDao = new AccountDao();

    /**
     * Создает счет пользователя по типу и пользователю
     *
     * @param typeCode код типа счета
     * @param userId   идентификатор пользователя
     * @return AccountDomain
     * @throws SQLException
     */
    public AccountDomain createAccountByTypeCodeAndUser(String typeCode, UUID userId) throws SQLException {
        AccountDomain accountDomain = new AccountDomain();
        accountDomain.setId(UUID.randomUUID());
        accountDomain.setTypeCode(typeCode);
        accountDomain.setUserId(userId);
        return accountDao.insert(accountDomain);
    }

    /**
     * Возвращает список всех счетов пользователя
     *
     * @param userLogin Логин пользователя
     * @return int количество счетов пользователя
     * @throws SQLException
     */
    public ArrayList<AccountDomain> findAllAccountsByUserLogin(String userLogin) throws SQLException {
        return accountDao.getAccountsByLogin(userLogin);
    }
}
