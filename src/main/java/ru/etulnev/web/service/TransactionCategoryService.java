package ru.etulnev.web.service;

import ru.etulnev.web.dao.TransactionCategoryDao;
import ru.etulnev.web.dao.domain.TransactionCategoryDomain;
import ru.etulnev.web.dto.TransactionCategoryDto;

import java.sql.SQLException;
import java.util.ArrayList;

import static ru.etulnev.web.dto.mapping.TransactionCategoryDtoMapping.mapTransactionCategoryDomainToDto;

public class TransactionCategoryService {
  TransactionCategoryDao transactionCategoryDao =  new TransactionCategoryDao();

  /**
   * Получает список типов счетов
   * @return ArrayList<TransactionCategoryDto>
   * @throws SQLException
   */
  public ArrayList<TransactionCategoryDto> getTransactionCategories() throws SQLException {
    ArrayList<TransactionCategoryDto> TransactionCategoryDtos = new ArrayList<>();
    ArrayList<TransactionCategoryDomain> transactionCategoryDomains = transactionCategoryDao.findAll();
    for (TransactionCategoryDomain transactionCategoryDomain : transactionCategoryDomains) {
      TransactionCategoryDto transactionCategoryDto = new TransactionCategoryDto();
      mapTransactionCategoryDomainToDto(transactionCategoryDomain, transactionCategoryDto);
      TransactionCategoryDtos.add(transactionCategoryDto);
    }
    return TransactionCategoryDtos;
  }

  /**
   * Создает тип счета
   * @param code код тип счета
   * @return Созданный тип счета
   * @throws SQLException возникшая ошибка
   */
  public TransactionCategoryDomain createTransactionCategory(String code, String name) throws SQLException {
    TransactionCategoryDomain transactionCategoryDomain = new TransactionCategoryDomain();
    transactionCategoryDomain.setCode(code);
    transactionCategoryDomain.setName(name);
    return transactionCategoryDao.insert(transactionCategoryDomain);
  }
}
