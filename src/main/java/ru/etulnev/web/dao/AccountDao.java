package ru.etulnev.web.dao;

import ru.etulnev.web.dao.domain.AccountDomain;
import ru.etulnev.web.dao.mapping.AccountMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static ru.etulnev.web.dao.DaoFactory.getConnection;

public class AccountDao implements Dao<AccountDomain, UUID> {
    AccountMapper accountMapper = new AccountMapper();
    private static AccountDao accountDao;

    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDao();
        }
        return accountDao;
    }

    /**
     * Получает счет по идентификатору
     * @param id идентификатор счета
     * @return найденный счет
     * @throws SQLException
     */
    @Override
    public AccountDomain findById(UUID id) throws SQLException {
        AccountDomain accountDomain = new AccountDomain();
        try (Connection connection = getConnection()) {
            String sql = "SELECT *\n" +
                    "FROM accounts\n" +
                    "WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                accountMapper.mapAccount(resultSet, accountDomain);
            }

        }
        return accountDomain;
    }

    @Override
    public ArrayList<AccountDomain> findAll() {
        return null;
    }


    /**
     * Добавляет счет
     * @param accountDomain добавляемый счет
     * @return созданный счет
     * @throws SQLException
     */
    @Override
    public AccountDomain insert(AccountDomain accountDomain) throws SQLException {
        UUID id = UUID.randomUUID();
        try (Connection connection = getConnection()) {
            String sql = "INSERT\n" +
                    "INTO accounts\n" +
                    "VALUES(?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            preparedStatement.setString(2, accountDomain.getTypeCode());
            preparedStatement.setDouble(3, accountDomain.getBalance());
            preparedStatement.setObject(4, accountDomain.getUserId());
            preparedStatement.executeUpdate();
        }
        return findById(id);
    }

    @Override
    public AccountDomain update(AccountDomain accountDomain) {
        return null;
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;
    }

    /**
     * Получает список счетов по логину пользователя
     * @param login
     * @return
     * @throws SQLException
     */
    public ArrayList<AccountDomain> getAccountsByLogin(String login) throws SQLException {
        try (Connection connection = getConnection()) {
            String sql = "SELECT *\n" +
                    "FROM accounts a\n" +
                    "JOIN users u ON a.user_id = u.id\n" +
                    "WHERE u.login = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<AccountDomain> accountDomains = new ArrayList<>();

            while (resultSet.next()) {
                AccountDomain accountDomain = new AccountDomain();
                accountMapper.mapAccount(resultSet, accountDomain);
                accountDomains.add(accountDomain);
            }

            return accountDomains;
        }
    }

    /**
     * Проверяет баланс средств на счете
     * @param conn соединение с БД
     * @param accountId идентификатор счета
     * @param amount сумма
     * @throws SQLException
     */
    public static void checkBalance(Connection conn, int accountId, double amount) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance < amount) {
                    throw new SQLException("Insufficient funds on account " + accountId);
                }
            } else {
                throw new SQLException("Account not found: " + accountId);
            }
        }
    }

    /**
     * Списание средств со счета
     * @param conn соединение с БД
     * @param accountId идентификатор счета
     * @param amount сумма списания
     * @throws SQLException
     */
    public static void withdraw(Connection conn, int accountId, double amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.setDouble(3, amount);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Withdrawal failed: account not found or insufficient funds");
            }
        }
    }

    /**
     * Списание средств со счета
     * @param conn соединение с БД
     * @param accountId идентификатор счета
     * @param amount сумма зачисления
     * @throws SQLException
     */
    public static void deposit(Connection conn, int accountId, double amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deposit failed: account not found");
            }
        }
    }

}
