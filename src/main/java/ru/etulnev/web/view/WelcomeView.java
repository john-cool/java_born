package ru.etulnev.web.view;

import ru.etulnev.web.service.PrintService;
import ru.etulnev.web.service.RegistrationService;
import java.sql.SQLException;
import java.util.Scanner;

public class WelcomeView {
  private static final PrintService printService = new PrintService();
  private final AuthorizationView authorizationView = new AuthorizationView();
  private final RegistrationService registrationService = new RegistrationService();
  Scanner scanner = new Scanner(System.in);

  public WelcomeView() {
  }

  public void greetings() throws SQLException {
    printService.printMessage("Укажите номер нужного пункта и нажмите \"Enter\": \n" +
      " 1 - авторизация пользователя\n" +
      " 2 - регистрация пользователя\n");

    int input = scanner.nextInt();
    if (input == 1) {
      scanner.nextLine(); //очистка буфера
      authorizationView.getAuthorization();
    } else if (input == 2) {
      scanner.nextLine(); //очистка буфера
      registrationService.registerUser();
    }
  }
}
