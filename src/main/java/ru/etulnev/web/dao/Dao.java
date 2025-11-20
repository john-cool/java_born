package ru.etulnev.web.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<DOMAIN, ID> {
  DOMAIN findById(ID id) throws SQLException;
  ArrayList<DOMAIN> findAll() throws SQLException;
  DOMAIN insert(DOMAIN domain) throws SQLException;
  DOMAIN update(DOMAIN domain);
  boolean delete(ID id);
}
