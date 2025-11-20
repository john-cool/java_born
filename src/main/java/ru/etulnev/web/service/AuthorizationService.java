package ru.etulnev.web.service;

import ru.etulnev.web.dao.UserDao;
import ru.etulnev.web.dto.UserDto;

public class AuthorizationService {
  private final UserDao userDao = UserDao.getUserDao();

  /**
   * Получает пользователя по логину и паролю
   * @param login логин пользователя
   * @param password пароль пользователя
   * @return User
   */
  public UserDto getUserByLoginAndPassword(String login, String password) {
    return userDao.getUserByLoginAndPassword(login, password);
  }
}
