package ru.etulnev.web.dto.mapping;

import ru.etulnev.web.dao.domain.AccountTypeDomain;
import ru.etulnev.web.dto.AccountTypeDto;

public class AccountTypeDtoMapping {

  /**
   * Преобразует DOMAIN в DTO
   * @param accountTypeDomain DOMAIN сущность
   * @param accountTypeDto DTO сущность
   */
  public static void mapAccountTypeDomainToDto(AccountTypeDomain accountTypeDomain, AccountTypeDto accountTypeDto) {
    accountTypeDto.setCode(accountTypeDomain.getCode());
    accountTypeDto.setName(accountTypeDomain.getName());
  }
}
