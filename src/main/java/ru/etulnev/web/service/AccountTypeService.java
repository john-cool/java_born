package ru.etulnev.web.service;

import ru.etulnev.web.dao.AccountTypeDao;
import ru.etulnev.web.dao.domain.AccountTypeDomain;
import ru.etulnev.web.dto.AccountTypeDto;

import java.sql.SQLException;
import java.util.ArrayList;

import static ru.etulnev.web.dto.mapping.AccountTypeDtoMapping.mapAccountTypeDomainToDto;

public class AccountTypeService {
  AccountTypeDao accountTypeDao = new AccountTypeDao();

  /**
   * Получает список типов счетов
   * @return ArrayList<AccountType>
   * @throws SQLException
   */
  public ArrayList<AccountTypeDto> getAccountTypes() throws SQLException {
    ArrayList<AccountTypeDto> accountTypeDtos = new ArrayList<>();
    ArrayList<AccountTypeDomain> accountTypeDomains = accountTypeDao.findAll();
    for (AccountTypeDomain accountTypeDomain : accountTypeDomains) {
      AccountTypeDto accountTypeDto = new AccountTypeDto();
      mapAccountTypeDomainToDto(accountTypeDomain, accountTypeDto);
      accountTypeDtos.add(accountTypeDto);
    }
    return accountTypeDtos;
  }

  /**
   * Создает тип счета
   * @param code код тип счета
   * @return Созданный тип счета
   * @throws SQLException возникшая ошибка
   */
  public AccountTypeDomain createAccountType(String code, String name) throws SQLException {
    AccountTypeDomain accountTypeDomain = new AccountTypeDomain();
    accountTypeDomain.setCode(code);
    accountTypeDomain.setName(name);
    return accountTypeDao.insert(accountTypeDomain);
  }
}
