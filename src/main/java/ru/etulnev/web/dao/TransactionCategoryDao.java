package ru.etulnev.web.dao;

import ru.etulnev.web.dao.domain.TransactionCategoryDomain;
import ru.etulnev.web.dao.mapping.TransactionCategoryMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static ru.etulnev.web.dao.DaoFactory.getConnection;

public class TransactionCategoryDao implements Dao<TransactionCategoryDomain, UUID> {
  TransactionCategoryMapper transactionCategoryMapper = new TransactionCategoryMapper();

  @Override
  public TransactionCategoryDomain findById(UUID uuid) {
    return null;
  }

  @Override
  public ArrayList<TransactionCategoryDomain> findAll() throws SQLException {
    ArrayList<TransactionCategoryDomain> transactionCategoryDomains = new ArrayList<>();
    try (Connection connection = getConnection()) {
      String sql = "SELECT * FROM transaction_categories";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        TransactionCategoryDomain transactionCategoryDomain = new TransactionCategoryDomain();
        transactionCategoryMapper.mapRsToTransactionCategoryDomain(resultSet, transactionCategoryDomain);
        transactionCategoryDomains.add(transactionCategoryDomain);
      }
    }
    return transactionCategoryDomains;
  }

  @Override
  public TransactionCategoryDomain insert(TransactionCategoryDomain transactionCategoryDomain) throws SQLException {
    try (Connection connection = getConnection()){

      String sql = "INSERT INTO transaction_categories (code, name) \n" +
        "VALUES (?, ?);";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, transactionCategoryDomain.getCode());
      preparedStatement.setString(2, transactionCategoryDomain.getName());
      preparedStatement.executeUpdate();

    }
    return findByCode(transactionCategoryDomain.getCode());
  }

  @Override
  public TransactionCategoryDomain update(TransactionCategoryDomain transactionCategoryDomain) {
    return null;
  }

  @Override
  public boolean delete(UUID uuid) {
    return false;
  }

  public TransactionCategoryDomain findByCode(String code) throws SQLException {
    TransactionCategoryDomain transactionCategoryDomain = new TransactionCategoryDomain();

    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM transaction_categories WHERE code = ?");
      statement.setString(1, code);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        transactionCategoryDomain.setCode(resultSet.getString("code"));
        transactionCategoryDomain.setName(resultSet.getString("name"));
      }
    }
    return transactionCategoryDomain;
  }
}
