package service;

import model.User;

import java.util.Scanner;

public class WelcomeService {
  private static final PrintService printService = new PrintService();
  private final AuthorizationService authorizationService = new AuthorizationService();
  private final RegistrationService registrationService = new RegistrationService();

  public WelcomeService() {
  }

  public void greetings() {
    printService.printMessage("Укажите нужный пункт и нажмите \"Enter\": \n" +
      " - авторизация: 1\n" +
      " - регистрация: 2");
    Scanner scanner = new Scanner(System.in);
    int input = scanner.nextInt();
    if (input == 1) {
      scanner.nextLine(); //очистка буфера
      authorizationService.getAuthorization();
    } else if (input == 2) {
      scanner.nextLine(); //очистка буфера
      registrationService.registerUser();
    }
  }


}
