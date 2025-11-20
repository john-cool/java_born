package ru.etulnev.web.service;
import java.sql.*;

import static ru.etulnev.web.dao.AccountDao.*;
import static ru.etulnev.web.dao.DaoFactory.getConnection;
import static ru.etulnev.web.service.TransactionService.createTransactionRecord;

public class TransferService {

    // Константы для типов операций
    private static final String TRANSACTION_TYPE_DEPOSIT = "deposit";
    private static final String TRANSACTION_TYPE_WITHDRAWAL = "withdrawal";
    private static final String TRANSACTION_CATEGORY = "transfer";

    /**
     * Переводит средства с одно счета на другой используя транзакции
     * @param fromAccountId счет, с которого переводятся средства
     * @param toAccountId счет для зачисления средств
     * @param amount сумма
     * @return boolean состоялся ли перевод
     */
    public boolean transferMoney(int fromAccountId, int toAccountId, double amount) {
        Connection conn = null;
        try {
            // 1. Устанавливаем соединение с базой данных
            conn = getConnection();

            // 2. Отключаем авто-коммит для управления транзакцией вручную
            conn.setAutoCommit(false);

            // 3. Проверяем достаточность средств на счете отправителя
            checkBalance(conn, fromAccountId, amount);

            // 4. Списываем средства с первого счета
            withdraw(conn, fromAccountId, amount);

            // 5. Зачисляем средства на второй счет
            deposit(conn, toAccountId, amount);

            // 6. Создаем записи в таблице transactions
            createTransactionRecord(conn, TRANSACTION_CATEGORY, amount, fromAccountId, TRANSACTION_TYPE_DEPOSIT);
            createTransactionRecord(conn, TRANSACTION_CATEGORY, amount, toAccountId, TRANSACTION_TYPE_WITHDRAWAL);

            // 7. Если все операции успешны - коммитим транзакцию
            conn.commit();
            System.out.println("Transfer successful!");
            return true;

        } catch (SQLException e) {
            // 8. В случае ошибки - откатываем транзакцию
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaction rolled back: " + e.getMessage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // 9. Восстанавливаем авто-коммит и закрываем соединение
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
