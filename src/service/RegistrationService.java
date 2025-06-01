package service;


import model.User;
import org.postgresql.util.MD5Digest;
import repository.UserRepository;

import java.util.Scanner;

public class RegistrationService {
  UserRepository userRepository = new UserRepository();
  PrintService printService = new PrintService();
  DigestService digestService = new DigestService();

  /**
   * Регистрирует пользователя
   */
  public void registerUser() {
    User user = new User();
    getUserFromConsole(user);
    userRepository.writeUser(user);
  }

  /**
   * Проверяет есть ли пользователь с таким login в БД
   * @param login логин пользователя
   */
  private boolean isUniqueUserLogin(String login) {
    return userRepository.checkUserLogin(login) == 0;
  }

  /**
   * Наполняет данными введенными с консоли сущность переданную сущность User
   */
  private void getUserFromConsole(User user) {
    Scanner scanner = new Scanner(System.in);
    printService.printMessage("Пожалуйста введите фамилию пользователя кириллицей с заглавной буквы: ");
    user.setSurname(scanner.next());
    printService.printMessage("Пожалуйста введите имя пользователя кириллицей с заглавной буквы: ");
    user.setName(scanner.next());
    printService.printMessage("Пожалуйста введите отчество пользователя кириллицей с заглавной буквы: ");
    user.setPatronymic(scanner.next());
    printService.printMessage("Пожалуйста введите email пользователя: ");
    user.setEmail(scanner.next());
    printService.printMessage("Пожалуйста введите login пользователя: ");
    String login = scanner.next();
    while (true) {
      if (isUniqueUserLogin(login)) {
        user.setLogin(login);
        break;
      }
      printService.printMessage("Пользователь с таким login уже есть в системе введите другой логин:");
      login = scanner.next();
    }

    printService.printMessage("Пожалуйста введите password пользователя: ");
    user.setPassword(digestService.hash(scanner.next()));
    scanner.close();
    printService.printMessage("Регистрация завершена успешно");
  }


}
