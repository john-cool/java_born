package ru.etulnev.web.service;

import ru.etulnev.web.dao.UserDao;

public class UserService {
  private static final UserDao userDao = new UserDao();

  /**
   * Проверяет есть ли пользователь с таким login в БД
   * @param login логин пользователя
   * @return boolean
   */
  public boolean isUniqueUserLogin(String login) {
    return userDao.checkUserLogin(login) == 0;
  }
}
