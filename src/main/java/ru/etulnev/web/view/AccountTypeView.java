package ru.etulnev.web.view;

import ru.etulnev.web.dao.domain.AccountTypeDomain;
import ru.etulnev.web.dto.AccountTypeDto;
import ru.etulnev.web.service.AccountTypeService;
import ru.etulnev.web.service.PrintService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static ru.etulnev.web.dto.mapping.AccountTypeDtoMapping.mapAccountTypeDomainToDto;
import static ru.etulnev.web.view.util.ViewUtil.getValidInput;

public class AccountTypeView {
    private static final AccountTypeService accountTypeService = new AccountTypeService();
    private static final PrintService printService = new PrintService();

    Scanner scanner = new Scanner(System.in);


    /**
     * Создает тип счета
     *
     * @throws SQLException ошибка возникшая при работе с БД
     */
    public void createAccountType() throws SQLException {
        String code = getValidInput(scanner, "код типа счета", false);
        String name = getValidInput(scanner, "название счета", true);
        AccountTypeDomain accountTypeDomain = accountTypeService.createAccountType(code, name);
        AccountTypeDto accountTypeDto = new AccountTypeDto();
        mapAccountTypeDomainToDto(accountTypeDomain, accountTypeDto);
        printService.printMessage("\n" + accountTypeDto);
    }

    /**
     * Выводит список типов счетов
     *
     * @throws SQLException
     */
    public static void showAccountTypes() throws SQLException {
        ArrayList<AccountTypeDto> accountTypeDomains = accountTypeService.getAccountTypes();
        printService.printMessage("Список существующих типов счетов:");
        accountTypeDomains.forEach(a -> {
            printService.printMessage(a.toString());
        });
    }

}
