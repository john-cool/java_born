package service;

import model.User;
import model.UserTransaction;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthorizationService {
  private final UserRepository userRepository = new UserRepository();
  private static final PrintService printService = new PrintService();
  private final DigestService digestService = new DigestService();
  private final Scanner scanner = new Scanner(System.in);

  /**
   * Авторизация пользователя
   */
  public void getAuthorization() {
    int count = 1;
    while (count <= 3) {
      count++;
      String login = getValidInput(scanner, "Логин");
      String password = getValidInput(scanner, "Пароль");

      User user = userRepository.getUserByLoginAndPassword(login, digestService.hash(password));

      if (user == null) {
        printService.printMessage("Пользователь с таким логином и паролем не найден, введите корректные данные");
        continue;
      }
      showUserAction(user);
      break;
    }


  }

  /**
   * Универсальный метод для ввода и валидации данных.
   *
   * @param scanner      Объект Scanner
   * @param stringPrefix Префикс к тексту
   * @return Валидная строка
   */
  private static String getValidInput(Scanner scanner, String stringPrefix) {
    String input;
    String errorMessage = stringPrefix + "не может быть пустым или содержать пробелы";
    String prompt = "Введите " + stringPrefix.toLowerCase() + ":";
    while (true) {
      printService.printMessage(prompt);
      input = scanner.nextLine().trim();

      if (input.isEmpty() || input.contains(" ")) {
        printService.printMessage(errorMessage + "\n");
      } else {
        break;
      }
    }
    return input;
  }

  private void showUserAction(User user) {
    printService.printMessage("Действия, выберите необходимое и нажмите \"Enter\": \n" +
      " - посмотреть все транзакции пользователя: 1\n" +
      " - посмотреть транзакции пользователя на конкретную дату: 2");

    if (scanner.hasNextInt() && scanner.nextInt() == 1) {
      scanner.nextLine(); //очистка буфера
      ArrayList<UserTransaction> userTransactions = userRepository.getUserTransaction(user);
      userTransactions.forEach(ut ->
        printService.printMessage(ut.getCategoryCode() + " " + ut.getDate() + " " + ut.getSum()));
    }
  }
}
