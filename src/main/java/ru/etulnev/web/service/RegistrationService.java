package ru.etulnev.web.service;


import ru.etulnev.web.dao.UserDao;
import ru.etulnev.web.dto.UserDto;
import ru.etulnev.web.view.RegistrationView;

import java.sql.SQLException;

public class RegistrationService {
  UserDao userDao = UserDao.getUserDao();
  RegistrationView registrationView = new RegistrationView();

  /**
   * Регистрирует пользователя
   */
  public void registerUser() throws SQLException {
    UserDto userDto = registrationView.getUserFromConsole();
    userDao.insert(userDto);
  }
}
