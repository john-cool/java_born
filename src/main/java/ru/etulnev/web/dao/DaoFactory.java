package ru.etulnev.web.dao;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {
  private static DataSource dataSource;
  public static DataSource getDataSource() {
    if (dataSource == null) {
      HikariDataSource hikariDataSource = new HikariDataSource();
      hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
      hikariDataSource.setUsername("postgres");
      hikariDataSource.setPassword("postgres");
      dataSource = hikariDataSource;
    }
    return dataSource;
  }

  public static Connection getConnection() throws SQLException {
    return getDataSource().getConnection();
  }

  private DaoFactory() {}
}
