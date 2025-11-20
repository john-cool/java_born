package ru.etulnev.web.dao.mapping;

import ru.etulnev.web.dto.TransactionCategoryDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionCategoryMapper {
  public void mapRsToTransactionCategoryDomain(ResultSet resultSet, TransactionCategoryDto transactionCategoryDomain) throws SQLException {
    transactionCategoryDomain.setCode(resultSet.getString("code"));
    transactionCategoryDomain.setName(resultSet.getString("name"));
  }
}
