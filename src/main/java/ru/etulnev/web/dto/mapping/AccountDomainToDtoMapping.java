package ru.etulnev.web.dto.mapping;

import ru.etulnev.web.dao.domain.AccountDomain;
import ru.etulnev.web.dto.AccountDto;

public class AccountDomainToDtoMapping {
    /**
     * Преобразует DOMAIN в DTO
     * @param accountDomain DOMAIN сущность
     * @param accountDto DTO сущность
     */
    public static void mapAccountDomainToDto(AccountDomain accountDomain, AccountDto accountDto) {
        accountDto.setId(accountDomain.getId());
        accountDto.setTypeCode(accountDomain.getTypeCode());
        accountDto.setBalance(accountDomain.getBalance());
        accountDto.setUserId(accountDomain.getUserId());
    }
}
