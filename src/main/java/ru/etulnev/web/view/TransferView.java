package ru.etulnev.web.view;

import ru.etulnev.web.service.AccountService;
import ru.etulnev.web.service.PrintService;

import java.sql.SQLException;

public class TransferView {
    AccountService accountService = new AccountService();
    private static final PrintService printService = new PrintService();
    AccountView accountView = new AccountView();

    /**
     * Выводит список всех счетов пользователя
     *
     * @param userLogin логин пользователя
     * @throws SQLException
     */
    public void getTransferParameters(String userLogin) throws SQLException {
        accountView.showAllAccountsByUserLogin(userLogin);
    }
}
