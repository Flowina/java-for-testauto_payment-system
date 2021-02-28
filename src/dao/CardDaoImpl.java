package dao;

import dao.utils.DaoUtils;
import entities.Account;
import entities.Card;
import entities.Client;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CardDaoImpl implements CardDao {

    private ConnectionFactory connectionFactory;

    public CardDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Card> findByClient(Client client) throws SQLException {
        List<Card> result = new LinkedList<>();
        final Connection con = connectionFactory.getConnection();
        final String sql = "SELECT c.id, c.accountId, c.card_number, c.owner_name, c.cvv, c.exp_year, c.exp_month FROM Cards c\n" +
                "INNER JOIN Accounts a ON c.accountId = a.id\n" +
                "INNER JOIN Clients cl ON cl.id = a.clientId AND cl.id = ?";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1, client.getId().get());
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                result.add(new Card(
                    DaoUtils.GetKey(rs, "id"),
                    rs.getLong("accountId"),
                    rs.getShort("card_number"),
                    rs.getString("owner_name"),
                    rs.getShort("cvv"),
                    rs.getShort("exp_year"),
                    rs.getShort("exp_month")
                ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Card> findByAccount(Account account) throws SQLException {
        List<Card> result = new LinkedList<>();
        final Connection con = connectionFactory.getConnection();
        final String sql = "SELECT c.id, c.accountId, c.card_number, c.owner_name, c.cvv, c.exp_year, c.exp_month FROM Cards c\n" +
                "WHERE c.accountId = ?";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1, account.getId().get());
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                result.add(new Card(
                        DaoUtils.GetKey(rs, "id"),
                        rs.getInt("accountId"),
                        rs.getShort("card_number"),
                        rs.getString("owner_name"),
                        rs.getShort("cvv"),
                        rs.getShort("exp_year"),
                        rs.getShort("exp_month")
                ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void create(Card card) throws SQLException {
        String sql = "INSERT INTO Cards(accountId,card_number,owner_name,cvv,exp_year,exp_month) VALUES(?,?,?,?,?,?)";

        try (Connection con = connectionFactory.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setLong(1, card.getAccountId());
            pstm.setLong(2, card.getCardNumber());
            pstm.setString(3, card.getOwnerName());
            pstm.setShort(4, card.getCvv());
            pstm.setShort(5, card.getExpYear());
            pstm.setShort(6, card.getExpMonth());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating card failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(DaoUtils.GetKey(generatedKeys, 1));
                } else {
                    throw new SQLException("Creating card failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Card> getAll() throws SQLException {
        List<Card> result = new LinkedList<>();
        final String sql = "SELECT * FROM cards;";
        try (Connection con = connectionFactory.getConnection();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {

                result.add(new Card(
                        DaoUtils.GetKey(rs, "id"),
                        rs.getLong("accountId"),
                        rs.getShort("card_number"),
                        rs.getString("owner_name"),
                        rs.getShort("cvv"),
                        rs.getShort("exp_year"),
                        rs.getShort("exp_month")
                ));
            }
        }
        return result;
    }

    @Override
    public Card findById(long id) throws SQLException {
        final String sql = "SELECT * FROM cards WHERE id = " + id;
        try (Connection con = connectionFactory.getConnection()) {
            //TODO: use preparedstatement
            final Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                return new Card(
                    DaoUtils.GetKey(rs, "id"),
                    rs.getInt("accountId"),
                    rs.getLong("card_number"),
                    rs.getString("owner_name"),
                    rs.getShort("cvv"),
                    rs.getShort("exp_year"),
                    rs.getShort("exp_month")
                );
            }
        }
        return null;
    }

    @Override
    public void update(Card card) throws SQLException {
        try (Connection con = connectionFactory.getConnection()) {
            String sql = "UPDATE Cards\n" +
                    "   SET accountId = ?\n" +
                    "      ,card_number = ?\n" +
                    "      ,owner_name = ?\n" +
                    "      ,cvv = ?\n" +
                    "      ,exp_year = ?\n" +
                    "      ,exp_month = ?\n" +
                    " WHERE id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1, card.getAccountId());
            pstm.setLong(2, card.getCardNumber());
            pstm.setString(3, card.getOwnerName());
            pstm.setShort(4, card.getCvv());
            pstm.setShort(5, card.getExpYear());
            pstm.setShort(6, card.getExpMonth());
            pstm.setLong(7, card.getId().get());

            int affectedRows = pstm.executeUpdate();
        }
    }

    @Override
    public void delete(Card card) throws SQLException {
        try (Connection con = connectionFactory.getConnection()) {
            String sql = "DELETE FROM cards WHERE id=? ;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1, card.getId().get());

            pstm.executeUpdate();
        }
    }
}
