package repository;

import model.User;
import model.UserTransaction;
import repository.mapping.UserMapper;
import repository.mapping.UserTransactionMapper;

import java.sql.*;
import java.util.ArrayList;


public class UserRepository {
  private Connection getConnection() {
    Connection connection;
    try {
      connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres",
        "postgres",
        "postgres");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return connection;
  }

  /**
   * Получает пользователя по логину и паролю
   * @param login логин
   * @param password пароль
   * @return User
   */
  public User getUserByLoginAndPassword(String login, String password) {
    UserMapper userMapper = new UserMapper();
    try {
      Connection connection = getConnection();
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

      User user = new User();
      userMapper.mapUser(resultSet, user);
      connection.close();
      return user;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Получает все транзакции пользователя
   *
   * @param user пользователь
   * @return ArrayList<UserTransaction>
   */
  public ArrayList<UserTransaction> getUserTransaction(User user) {
    UserTransactionMapper userTransactionMapper = new UserTransactionMapper();
    ArrayList<UserTransaction> userTransactions = new ArrayList<>();

    try {
      Connection connection = getConnection();

      String sql = "SELECT *\n" +
        "FROM transactions t\n" +
        "JOIN accounts a on t.account_id = a.id\n" +
        "WHERE a.user_id = ?";

      PreparedStatement preparedStatement = connection.prepareStatement(
        sql);
      preparedStatement.setObject(1, user.getId());
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        UserTransaction userTransaction = new UserTransaction();
        userTransactionMapper.mapUserTransaction(resultSet, userTransaction);
        userTransactions.add(userTransaction);
      }
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return userTransactions;
  }

  /**
   * Сохраняет регистрационные данные пользователя в БД
   * @param user пользователь
   */
  public void writeUser(User user) {
    try {
      Connection connection = getConnection();

      String sql = "INSERT INTO users (id, surname, name, patronymic, email, login, password) \n" +
        "VALUES (uuid_generate_v4(), ?, ?, ?, ?, ?, ?);";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setObject(1, user.getSurname());
      preparedStatement.setString(2, user.getName());
      preparedStatement.setString(3, user.getPatronymic());
      preparedStatement.setString(4, user.getEmail());
      preparedStatement.setString(5, user.getLogin());
      preparedStatement.setString(6, user.getPassword());
      preparedStatement.executeUpdate();

      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Проверяет наличие логина в БД
   *
   * @param login логин пользователя
   * @return int
   */
  public int checkUserLogin(String login) {
    try {
      Connection connection = getConnection();

      String sql = "SELECT count(*)\n" +
        "FROM users\n" +
        "WHERE login = ?";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, login);
      ResultSet resultSet = preparedStatement.executeQuery();
      int res = resultSet.next() ? resultSet.getInt(1) : 0;
      connection.close();

      return res;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
