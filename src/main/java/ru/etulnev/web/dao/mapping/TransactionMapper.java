package ru.etulnev.web.dao.mapping;

import ru.etulnev.web.dto.TransactionDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper {
  public static void mapUserTransaction(ResultSet resultSet, TransactionDto transactionDto) {
    try {
      transactionDto.setId(resultSet.getString("id"));
      transactionDto.setCategoryCode(resultSet.getString("category_code"));
      transactionDto.setDate(resultSet.getDate("date"));
      transactionDto.setSum(resultSet.getInt("sum"));
      transactionDto.setAccount_id(resultSet.getString("account_id"));
      transactionDto.setType(resultSet.getString("type"));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
