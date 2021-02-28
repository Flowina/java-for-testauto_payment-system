package dao;

import entities.Account;
import entities.Client;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDaoImpl implements AccountDao {
    private ConnectionFactory connectionFactory;

    public AccountDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Account> findByClient(Client client) throws SQLException {
        List<Account> result = new LinkedList<>();
        final Connection con = connectionFactory.getConnection();
        final String sql = "SELECT * FROM accounts WHERE clientId = " + client.getId().get();
        try {
            //TODO: use preparedstatement
            final Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                result.add(new Account(
                        Optional.of(rs.getLong("id")),
                        rs.getLong("clientId"),
                        rs.getInt("number"),
                        rs.getShort("type"),
                        rs.getDouble("amount"),
                        new Date(rs.getDate("opening_date").getTime()),
                        rs.getDate("closing_date") == null
                                ? null
                                : new Date(rs.getDate("closing_date").getTime())
                ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void create(Account account) throws SQLException {
        /*String query = "INSERT INTO accounts " +
                "(clientId, number, type, amount, opening_date, closing_date) " +
                "VALUES ("
                + account.getClientId() + ","
                + account.getNumber() + ","
                + account.getType() + ","
                + account.getAmount() + ", "
                + "'" + account.getOpeningDate().toString() + "',"
                + "'" + account.getClosingDate() == null ? "NULL" : account.getClosingDate().toString() + "'";*/
        String sql = "INSERT INTO accounts (clientId, number, type, amount, opening_date, closing_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = connectionFactory.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setLong(1, account.getClientId());
            pstm.setInt(2, account.getNumber());
            pstm.setShort(3, account.getType());
            pstm.setDouble(4, account.getAmount());
            pstm.setDate(5, new java.sql.Date(account.getOpeningDate().getTime()));
            pstm.setDate(6, account.getClosingDate() == null
                    ? null
                    : new java.sql.Date(account.getClosingDate().getTime()));

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(Optional.of(generatedKeys.getLong(1)));
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Account> getAll() throws SQLException {
        List<Account> result = new LinkedList<>();
        final String sql = "SELECT * FROM accounts;";
        final Connection con = connectionFactory.getConnection();
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                //public Account(int id, int clientId, int number, short type, double amount, Date openingDate, Date closingDate)
                result.add(new Account(
                        Optional.of(rs.getLong("id")),
                        rs.getLong("clientId"),
                        rs.getInt("number"),
                        rs.getShort("type"),
                        rs.getDouble("amount"),
                        new Date(rs.getDate("opening_date").getTime()),
                        rs.getDate("closing_date") == null
                                ? null
                                : new Date(rs.getDate("closing_date").getTime())
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    @Override
    public Account findById(long id) throws SQLException {

        final Connection con = connectionFactory.getConnection();
        final String sql = "SELECT * FROM accounts WHERE id = " + id;
        try {
            //TODO: use preparedstatement
            final Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                return new Account(
                        Optional.of(rs.getLong("id")),
                        rs.getLong("clientId"),
                        rs.getInt("number"),
                        rs.getShort("type"),
                        rs.getDouble("amount"),
                        new java.util.Date(rs.getDate("opening_date").getTime()),
                        rs.getDate("closing_date") == null
                            ? null
                            : new java.util.Date(rs.getDate("closing_date").getTime())

                );
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void update(Account account) throws SQLException {
        try (Connection con = connectionFactory.getConnection()) {
            String sql = "UPDATE accounts\n" +
                    "   SET clientId = ?\n" +
                    "      ,number = ?\n" +
                    "      ,type = ?\n" +
                    "      ,amount = ?\n" +
                    "      ,opening_date = ?\n" +
                    "      ,closing_date = ?\n" +
                    " WHERE id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1, account.getClientId());
            pstm.setInt(2, account.getNumber());
            pstm.setShort(3, account.getType());
            pstm.setDouble(4, account.getAmount());
            pstm.setDate(5, new java.sql.Date(account.getOpeningDate().getTime()));
            pstm.setDate(6, account.getClosingDate() == null
                    ? null
                    : new java.sql.Date(account.getClosingDate().getTime()));
            pstm.setLong(7, account.getId().get());
            int affectedRows = pstm.executeUpdate();
        }
    }

    @Override
    public void delete(Account account) throws SQLException {
        try(Connection con = connectionFactory.getConnection()) {
            String sql = "DELETE FROM accounts WHERE id=? ;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1, account.getId().get());

            pstm.executeUpdate();
        }
    }
}
