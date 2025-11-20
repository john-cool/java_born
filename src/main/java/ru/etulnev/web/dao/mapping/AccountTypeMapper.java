package ru.etulnev.web.dao.mapping;

import ru.etulnev.web.dto.AccountTypeDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountTypeMapper {
  public void mapAccountType(ResultSet resultSet, AccountTypeDto accountTypeDomain) throws SQLException {
    accountTypeDomain.setCode(resultSet.getString("code"));
    accountTypeDomain.setName(resultSet.getString("name"));
  }
}
