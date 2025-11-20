package ru.etulnev.web.dao;

import ru.etulnev.web.dao.domain.AccountTypeDomain;
import ru.etulnev.web.dao.mapping.AccountTypeMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static ru.etulnev.web.dao.DaoFactory.getConnection;

public class AccountTypeDao implements Dao<AccountTypeDomain, UUID> {
  AccountTypeMapper accountTypeMapper = new AccountTypeMapper();

  @Override
  public AccountTypeDomain findById(UUID id) throws SQLException {
    return null;
  }

  public AccountTypeDomain findByCode(String code) throws SQLException {
    AccountTypeDomain accountTypeDomain = new AccountTypeDomain();

    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM account_types WHERE code = ?");
      statement.setString(1, code);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        accountTypeDomain.setCode(resultSet.getString("code"));
        accountTypeDomain.setName(resultSet.getString("name"));
      }
    }
    return accountTypeDomain;
  }

  /**
   * Получает список типов счетов
   * @return ArrayList<AccountType>
   */
  public ArrayList<AccountTypeDomain> findAll() throws SQLException {
    ArrayList<AccountTypeDomain> accountTypeDomains = new ArrayList<>();
    try (Connection connection = getConnection()) {
      String sql = "SELECT * FROM account_types";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        AccountTypeDomain accountTypeDomain = new AccountTypeDomain();
        accountTypeMapper.mapAccountType(resultSet, accountTypeDomain);
        accountTypeDomains.add(accountTypeDomain);
      }
    }
    return accountTypeDomains;
  }


  @Override
  public AccountTypeDomain insert(AccountTypeDomain accountTypeDomain) throws SQLException {
    try (Connection connection = getConnection()){

      String sql = "INSERT INTO account_types (code, name) \n" +
        "VALUES (?, ?);";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, accountTypeDomain.getCode());
      preparedStatement.setString(2, accountTypeDomain.getName());
      preparedStatement.executeUpdate();

    }
    return findByCode(accountTypeDomain.getCode());
  }

  @Override
  public AccountTypeDomain update(AccountTypeDomain accountTypeDomain) {
    return null;
  }

  @Override
  public boolean delete(UUID uuid) {
    return false;
  }
}
