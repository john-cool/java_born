package repository.mapping;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserMapper {
  public void mapUser(ResultSet resultSet, User user) throws SQLException {
    user.setId(resultSet.getObject("id", UUID.class));
    user.setSurname(resultSet.getString("surname"));
    user.setName(resultSet.getString("name"));
    user.setPatronymic(resultSet.getString("patronymic"));
    user.setEmail(resultSet.getString("email"));
    user.setLogin(resultSet.getString("login"));
    user.setPassword(resultSet.getString("password"));
  }
}
