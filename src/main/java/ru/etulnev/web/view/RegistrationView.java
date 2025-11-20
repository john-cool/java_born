package ru.etulnev.web.view;

import ru.etulnev.web.dto.UserDto;
import ru.etulnev.web.service.DigestService;
import ru.etulnev.web.service.PrintService;
import ru.etulnev.web.service.UserService;

import java.util.Scanner;

public class RegistrationView {
  PrintService printService = new PrintService();
  UserService userService = new UserService();
  DigestService digestService = new DigestService();
  Scanner scanner = new Scanner(System.in);

  /**
   * Наполняет данными введенными с консоли сущность User
   * @return User Пользователь
   */
  public UserDto getUserFromConsole() {
    UserDto userDto = new UserDto();
    printService.printMessage("Пожалуйста введите фамилию пользователя кириллицей с заглавной буквы: ");
    userDto.setSurname(scanner.next());
    printService.printMessage("Пожалуйста введите имя пользователя кириллицей с заглавной буквы: ");
    userDto.setName(scanner.next());
    printService.printMessage("Пожалуйста введите отчество пользователя кириллицей с заглавной буквы: ");
    userDto.setPatronymic(scanner.next());
    printService.printMessage("Пожалуйста введите email пользователя: ");
    userDto.setEmail(scanner.next());
    printService.printMessage("Пожалуйста введите login пользователя: ");
    String login = scanner.next();
    while (true) {
      if (userService.isUniqueUserLogin(login)) {
        userDto.setLogin(login);
        break;
      }
      printService.printMessage("Пользователь с таким login уже есть в системе введите другой логин:");
      login = scanner.next();
    }

    printService.printMessage("Пожалуйста введите password пользователя: ");
    userDto.setPassword(digestService.hash(scanner.next()));
    scanner.close();
    printService.printMessage("Регистрация завершена успешно");
    return userDto;
  }
}
