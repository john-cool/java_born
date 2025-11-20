package ru.etulnev.web.view;

import ru.etulnev.web.dao.domain.TransactionCategoryDomain;
import ru.etulnev.web.dto.TransactionCategoryDto;
import ru.etulnev.web.service.PrintService;
import ru.etulnev.web.service.TransactionCategoryService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static ru.etulnev.web.dto.mapping.TransactionCategoryDtoMapping.mapTransactionCategoryDomainToDto;
import static ru.etulnev.web.view.util.ViewUtil.getValidInput;

public class TransactionCategoryView {
  private static final PrintService printService = new PrintService();
  private static final TransactionCategoryService transactionCategoryService = new TransactionCategoryService();

  Scanner scanner = new Scanner(System.in);


  /**
   * Создает категорию транзакции
   *
   * @throws SQLException ошибка возникшая при работе с БД
   */
  public void createTransactionCategory() throws SQLException {
    String code = getValidInput(scanner, "код категории транзакции", false);
    String name = getValidInput(scanner, "название категории транзакции", true);
    TransactionCategoryDomain transactionCategoryDomain = transactionCategoryService.createTransactionCategory(code, name);
    TransactionCategoryDto transactionCategoryDto = new TransactionCategoryDto();
    mapTransactionCategoryDomainToDto(transactionCategoryDomain, transactionCategoryDto);
    printService.printMessage("\n" + transactionCategoryDto);
  }

  public void showTransactionCategories() throws SQLException {
    ArrayList<TransactionCategoryDto> transactionCategoryDtos = transactionCategoryService.getTransactionCategories();
    printService.printMessage("Список категорий транзакций:");
    transactionCategoryDtos.forEach(a -> {
      printService.printMessage(a.toString());
    });
  }
}
