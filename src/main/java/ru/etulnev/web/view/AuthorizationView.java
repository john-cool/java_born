package ru.etulnev.web.view;

import ru.etulnev.web.dto.AccountDto;
import ru.etulnev.web.dto.TransactionDto;
import ru.etulnev.web.dto.UserDto;
import ru.etulnev.web.service.AuthorizationService;
import ru.etulnev.web.service.DigestService;
import ru.etulnev.web.service.PrintService;
import ru.etulnev.web.service.TransactionService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static ru.etulnev.web.view.AccountTypeView.showAccountTypes;
import static ru.etulnev.web.view.util.ViewUtil.getValidInput;

public class AuthorizationView {
    static PrintService printService = new PrintService();
    AuthorizationService authorizationService = new AuthorizationService();
    DigestService digestService = new DigestService();
    TransactionService transactionService = new TransactionService();
    TransactionCategoryView transactionCategoryView = new TransactionCategoryView();
    AccountView accountView = new AccountView();
    AccountTypeView accountTypeView = new AccountTypeView();
    Scanner scanner = new Scanner(System.in);

    int input;

    /**
     * Авторизация пользователя
     */
    public void getAuthorization() throws SQLException {
        int count = 1;
        while (count <= 3) {
            count++;
            String login = getValidInput(scanner, "Логин", false);
            String password = getValidInput(scanner, "Пароль", false);

            UserDto userDto = authorizationService.getUserByLoginAndPassword(login, digestService.hash(password));

            if (userDto == null) {
                printService.printMessage("Пользователь с таким логином и паролем не найден, введите корректные данные");
                continue;
            }
            showUserAction(userDto);
            break;
        }
    }


    private void showUserAction(UserDto userDto) throws SQLException {
        showMenu();

        switch (input) {
            case 1:
                scanner.nextLine(); //очистка буфера
                ArrayList<TransactionDto> transactionDtos = transactionService.getTransactionsByUserId(userDto.getId());
                if (transactionDtos.isEmpty()) {
                    printService.printMessage("\n Для пользователя " + userDto.getSurname() + " " + userDto.getName() + " транзакции не найдены");
                }
                transactionDtos.forEach(ut ->
                        printService.printMessage(ut.getCategoryCode() + " " + ut.getDate() + " " + ut.getSum()));
                showUserAction(userDto);
                break;
            case 2:
                scanner.nextLine(); //очистка буфера
                AccountDto accountDto = accountView.createAccountByUser(userDto);
                printService.printMessage("Создан " + accountDto.toString());
                showUserAction(userDto);
                break;
            case 3:
                scanner.nextLine(); //очистка буфера
                showAccountTypes();
                showUserAction(userDto);
                break;
            case 4:
                scanner.nextLine(); //очистка буфера
                accountTypeView.createAccountType();
                showUserAction(userDto);
                break;
            case 5:
                scanner.nextLine(); //очистка буфера
                transactionCategoryView.showTransactionCategories();
                showUserAction(userDto);
                break;
            case 6:
                scanner.nextLine(); //очистка буфера
                transactionCategoryView.createTransactionCategory();
                showUserAction(userDto);
                break;
            case 7:
                scanner.nextLine();
                accountView.showAllAccountsByUserLogin(userDto.getLogin());
                break;
            case 8:
                scanner.nextLine();
                accountView.showAllAccountsByUserLogin(userDto.getLogin());
                break;
            case 10:
                scanner.nextLine();
                break;
        }

    }

    /**
     * Отображает меню пользователя
     */
    private void showMenu() {
        printService.printMessage("\nДействия, выберите необходимое и нажмите \"Enter\": \n" +
                " 1 - посмотреть все транзакции пользователя\n" +
                " 2 - создать счет для пользователя\n" +
                " 3 - отобразить типы счетов\n" +
                " 4 - создать новый тип счета\n" +
                " 5 - отобразить категории транзакций\n" +
                " 6 - создать новую категорию транзакций\n" +
                " 7 - посмотреть все счета пользователя\n" +
                " 8 - перевод средств\n" +
                " 10 - выход\n");

        input = scanner.nextInt();
    }

}

