package ru.etulnev.web.dao;

import ru.etulnev.web.dao.domain.UserDomain;
import ru.etulnev.web.dao.mapping.UserMapper;
import ru.etulnev.web.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static ru.etulnev.web.dao.DaoFactory.getConnection;

public class UserDao implements Dao<UserDto, UUID> {
  UserMapper userMapper = new UserMapper();
  private static UserDao userDao;

  public static UserDao getUserDao() {
    if (userDao == null) {
      userDao = new UserDao();
    }
    return userDao;
  }

  @Override
  public UserDomain findById(UUID id) throws SQLException {
    UserDomain userDomain = null;

    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
      statement.setObject(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        userDomain = new UserDomain();
        userMapper.mapUser(resultSet, userDomain);
      }
    }
    return userDomain;
  }

  @Override
  public ArrayList<UserDto> findAll() {
    return null;
  }

  @Override
  public UserDto update(UserDto userDto) {
    return null;
  }

  @Override
  public boolean delete(UUID uuid) {
    return false;
  }

  /**
   * Получает пользователя по логину и паролю
   * @param login логин
   * @param password пароль
   * @return User
   */
  public UserDomain getUserByLoginAndPassword(String login, String password) {
    try (Connection connection = getConnection()) {

      String sql = "select *\n" +
        "from users\n" +
        "where login = ?\n" +
        "  and password = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(
        sql,
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY);
      preparedStatement.setString(1, login);
      preparedStatement.setString(2, password);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!resultSet.first()) {
        return null;
      }

      UserDomain userDomain = new UserDomain();
      userMapper.mapUser(resultSet, userDomain);

      return userDomain;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Сохраняет регистрационные данные пользователя в БД
   * @param userDto пользователь
   */
  public UserDto insert(UserDto userDto) throws SQLException {
    UUID id = UUID.randomUUID();
    try (Connection connection = getConnection()){

      String sql = "INSERT INTO users (id, surname, name, patronymic, email, login, password) \n" +
        "VALUES (uuid_generate_v4(), ?, ?, ?, ?, ?, ?);";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setObject(1, id);
      preparedStatement.setObject(1, userDto.getSurname());
      preparedStatement.setString(2, userDto.getName());
      preparedStatement.setString(3, userDto.getPatronymic());
      preparedStatement.setString(4, userDto.getEmail());
      preparedStatement.setString(5, userDto.getLogin());
      preparedStatement.setString(6, userDto.getPassword());
      preparedStatement.executeUpdate();

    }
    return findById(id);
  }

  /**
   * Проверяет наличие логина в БД
   *
   * @param login логин пользователя
   * @return int
   */
  public int checkUserLogin(String login) {
    try (Connection connection = getConnection()) {

      String sql = "SELECT count(*)\n" +
        "FROM users\n" +
        "WHERE login = ?";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, login);
      ResultSet resultSet = preparedStatement.executeQuery();

      return resultSet.next() ? resultSet.getInt(1) : 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
