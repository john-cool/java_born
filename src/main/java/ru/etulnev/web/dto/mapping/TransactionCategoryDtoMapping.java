package ru.etulnev.web.dto.mapping;

import ru.etulnev.web.dao.domain.TransactionCategoryDomain;
import ru.etulnev.web.dto.TransactionCategoryDto;

public class TransactionCategoryDtoMapping {

  /**
   * Преобразует DOMAIN в DTO
   * @param transactionCategoryDomain DOMAIN сущность
   * @param transactionCategoryDto DTO сущность
   */
  public static void mapTransactionCategoryDomainToDto(TransactionCategoryDomain transactionCategoryDomain, TransactionCategoryDto transactionCategoryDto) {
    transactionCategoryDto.setCode(transactionCategoryDomain.getCode());
    transactionCategoryDto.setName(transactionCategoryDomain.getName());
  }

}
