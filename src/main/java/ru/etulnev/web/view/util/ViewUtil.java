package ru.etulnev.web.view.util;

import ru.etulnev.web.service.PrintService;

import java.util.Scanner;

public class ViewUtil {
  private static final PrintService printService = new PrintService();

  /**
   * Универсальный метод для ввода и валидации данных.
   *
   * @param scanner      Объект Scanner
   * @param stringPrefix Префикс к тексту
   * @return Валидная строка
   */
  public static String getValidInput(Scanner scanner, String stringPrefix, Boolean isName) {
    String input;
    String errorMessage = stringPrefix + "не может быть пустым или содержать пробелы";
    String prompt = "Введите " + stringPrefix.toLowerCase() + ":";
    while (true) {
      printService.printMessage(prompt);
      input = scanner.nextLine().trim();

      if (input.isEmpty() && isContainOnlyDigitLatinAlphabetChar(input) && !isName) {
        printService.printMessage(errorMessage + "\n");
      } else if (!isContainOnlyDigitLatinAlphabetChar(input) && !isName) {
        printService.printMessage("Может содержать только символы латинского алфавита и цифры\n");
      } else {
        break;
      }
    }
    return input;
  }

  /**
   * Проверяет, содержит ли переданная строка только символы латинского алфавита и цифры
   * @param input переданная строка
   * @return boolean
   */
  public static boolean isContainOnlyDigitLatinAlphabetChar(String input) {
    return input.matches("^[a-zA-Z0-9]+$");
  }
}
