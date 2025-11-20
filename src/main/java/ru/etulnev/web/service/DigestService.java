package ru.etulnev.web.service;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * Сервис создания хэша пароля
 */
public class DigestService {
  public String hash(String password) {
    return md5Hex(password);
  }
}
