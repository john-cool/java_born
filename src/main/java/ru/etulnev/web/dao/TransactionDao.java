package ru.etulnev.web.dao;

import ru.etulnev.web.dao.mapping.TransactionMapper;
import ru.etulnev.web.dto.TransactionDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static ru.etulnev.web.dao.DaoFactory.getConnection;

public class TransactionDao implements Dao<ru.etulnev.web.dao.domain.TransactionDomain, UUID> {
    @Override
    public ru.etulnev.web.dao.domain.TransactionDomain findById(UUID uuid) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<ru.etulnev.web.dao.domain.TransactionDomain> findAll() throws SQLException {
        return null;
    }

    @Override
    public ru.etulnev.web.dao.domain.TransactionDomain insert(ru.etulnev.web.dao.domain.TransactionDomain transactionDomain) throws SQLException {
        return null;
    }

    @Override
    public ru.etulnev.web.dao.domain.TransactionDomain update(ru.etulnev.web.dao.domain.TransactionDomain transactionDomain) {
        return null;
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;
    }


    /**
     * Получает все транзакции пользователя
     *
     * @param userId идентификатор пользователь
     * @return ArrayList<UserTransaction>
     */
    public ArrayList<TransactionDto> getTransactionsByUserId(UUID userId) throws SQLException {
        ArrayList<TransactionDto> transactionDomains = new ArrayList<>();

        try (Connection connection = getConnection()) {

            String sql = "SELECT *\n" +
                    "FROM transactions t\n" +
                    "JOIN accounts a ON t.account_id = a.id\n" +
                    "WHERE a.user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql);
            preparedStatement.setObject(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TransactionDto transactionDto = new TransactionDto();
                TransactionMapper.mapUserTransaction(resultSet, transactionDto);
                transactionDomains.add(transactionDto);
            }

        }

        return transactionDomains;
    }

    /**
     * Создает запись в таблице транзакций
     * @param conn соединение с БД
     * @param accountId идентификатор счета
     * @param sum сумма
     * @param type тип транзакции
     * @param transactionCategory категория транзакции
     * @throws SQLException
     */
    public void createTransactionRecord(Connection conn, String transactionCategory, double sum, int accountId,
                                         String type) throws SQLException {
        String sql = "INSERT INTO transactions (id, category_code, date, sum, account_id, type) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        UUID id = UUID.randomUUID();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.setString(2, transactionCategory);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setDouble(4, sum);
            stmt.setInt(5, accountId);
            stmt.setString(6, type);

            stmt.executeUpdate();
        }
    }
}
