package ru.etulnev.web.service;

import ru.etulnev.web.dao.TransactionDao;
import ru.etulnev.web.dto.TransactionDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class TransactionService {
    static TransactionDao transactionDao = new TransactionDao();

    /**
     * Получает список транзакций пользователя
     *
     * @param userId идентификатор пользователя
     * @return ArrayList<UserTransactions> список транзакций пользователя
     * @throws SQLException
     */
    public ArrayList<TransactionDto> getTransactionsByUserId(UUID userId) throws SQLException {
        return transactionDao.getTransactionsByUserId(userId);
    }

    /**
     * Создает запись в таблице транзакций
     *
     * @param conn                соединение с БД
     * @param accountId           идентификатор счета
     * @param sum                 сумма
     * @param type                тип транзакции
     * @param transactionCategory категория транзакции
     * @throws SQLException
     */
    public static void createTransactionRecord(
            Connection conn, String transactionCategory, double sum, int accountId, String type) throws SQLException {
        transactionDao.createTransactionRecord(conn, transactionCategory, sum, accountId, type);
    }
}
