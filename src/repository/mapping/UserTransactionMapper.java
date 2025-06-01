package repository.mapping;

import model.UserTransaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTransactionMapper {
  public void mapUserTransaction(ResultSet resultSet, UserTransaction userTransaction) {
    try {
      userTransaction.setId(resultSet.getString("id"));
      userTransaction.setCategoryCode(resultSet.getString("category_code"));
      userTransaction.setDate(resultSet.getDate("date"));
      userTransaction.setSum(resultSet.getInt("sum"));
      userTransaction.setAccount_id(resultSet.getString("account_id"));
      userTransaction.setType(resultSet.getString("type"));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
