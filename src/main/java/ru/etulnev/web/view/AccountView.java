package ru.etulnev.web.view;

import ru.etulnev.web.dao.domain.AccountDomain;
import ru.etulnev.web.dto.AccountDto;
import ru.etulnev.web.dto.UserDto;
import ru.etulnev.web.service.AccountService;
import ru.etulnev.web.service.PrintService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static ru.etulnev.web.view.AccountTypeView.showAccountTypes;
import static ru.etulnev.web.view.util.ViewUtil.getValidInput;
import static ru.etulnev.web.dto.mapping.AccountDomainToDtoMapping.mapAccountDomainToDto;

public class AccountView {
    AccountService accountService = new AccountService();
    private static final PrintService printService = new PrintService();

    Scanner scanner = new Scanner(System.in);


    /**
     * Создает счет для переданного пользователя
     *
     * @param userDto пользователь
     * @return AccountDto созданный пользователь
     * @throws SQLException
     */
    public AccountDto createAccountByUser(UserDto userDto) throws SQLException {
        int countUserAccounts = accountService.findAllAccountsByUserLogin(userDto.getLogin()).size();
        if (countUserAccounts == 5) {
            printService.printMessage("Пользователь " + userDto.getSurname() + " " + userDto.getName() + " имеет " + countUserAccounts + " счетов");
            printService.printMessage("Количество счетов одного пользователя не должно быть больше 5");
        }

        showAccountTypes();

        String typeCode = getValidInput(scanner, "код типа счета", false);


        return accountService.createAccountByTypeCodeAndUser(typeCode, userDto.getId());
    }

    /**
     * Выводит список всех счетов пользователя
     *
     * @param userLogin логин пользователя
     * @throws SQLException
     */
    public void showAllAccountsByUserLogin(String userLogin) throws SQLException {
        ArrayList<AccountDomain> accountDomains = accountService.findAllAccountsByUserLogin(userLogin);
        ArrayList<AccountDto> accountDtos = new ArrayList<>();
        for (AccountDomain accountDomain : accountDomains) {
            AccountDto accountDto = new AccountDto();
            mapAccountDomainToDto(accountDomain, accountDto);
            accountDtos.add(accountDto);
        }
        accountDtos.forEach(accountDto -> {
            printService.printMessage(accountDto.toString());
        });
    }
}
