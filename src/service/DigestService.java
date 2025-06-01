package service;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class DigestService {
  public String hash(String password) {
    return md5Hex(password);
  }
}
