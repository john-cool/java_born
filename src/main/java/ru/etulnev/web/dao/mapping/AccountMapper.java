package ru.etulnev.web.dao.mapping;

import ru.etulnev.web.dao.domain.AccountDomain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccountMapper {
  public void mapAccount(ResultSet resultSet, AccountDomain accountDomain) throws SQLException {
    accountDomain.setId(resultSet.getObject("id", UUID.class));
    accountDomain.setTypeCode(resultSet.getString("type_code"));
    accountDomain.setBalance(resultSet.getDouble("balance"));
    accountDomain.setUserId(resultSet.getObject("user_id", UUID.class));
  }
}
