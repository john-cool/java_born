package ru.etulnev.web.dao.mapping;

import ru.etulnev.web.dao.domain.UserDomain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserMapper {
  public void mapUser(ResultSet resultSet, UserDomain userDomain) throws SQLException {
    userDomain.setId(resultSet.getObject("id", UUID.class));
    userDomain.setSurname(resultSet.getString("surname"));
    userDomain.setName(resultSet.getString("name"));
    userDomain.setPatronymic(resultSet.getString("patronymic"));
    userDomain.setEmail(resultSet.getString("email"));
    userDomain.setLogin(resultSet.getString("login"));
    userDomain.setPassword(resultSet.getString("password"));
  }
}
