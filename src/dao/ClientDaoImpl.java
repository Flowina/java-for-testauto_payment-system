package dao;

import dao.utils.DaoUtils;
import entities.Client;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: Close all connections and statements
public class ClientDaoImpl implements ClientDao {
    private ConnectionFactory connectionFactory;

    public ClientDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void create(Client client) throws SQLException {
        try (Connection con = connectionFactory.getConnection()) {
            String sql = "INSERT INTO clients (lastName, firstName, dateOfBirth) VALUES (?, ?, ?) ";

            PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, client.getLastName());
            pstm.setString(2, client.getFirstName());
            pstm.setDate(3, new java.sql.Date(client.getDateOfBirth().getTime()));

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(DaoUtils.GetKey(generatedKeys, 1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Client> getAll() throws SQLException {
        List<Client> result = new LinkedList<>();
        final String sql = "SELECT * FROM clients;";
        final Connection con = connectionFactory.getConnection();
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new Client(
                        DaoUtils.GetKey(rs, "id"),
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getDate("dateOfBirth")));
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
    public Client findById(long id) throws SQLException {
        final Connection con = connectionFactory.getConnection();
        final String sql = "SELECT * FROM clients WHERE id = " + id;
        try {
            //TODO: use preparedstatement
            final Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                return new Client(
                        DaoUtils.GetKey(rs, "id"),
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getDate("dateOfBirth"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Client client) throws SQLException {
        try (Connection con = connectionFactory.getConnection()) {
            String sql = "UPDATE clients SET " +
                    "lastName=?," +
                    "firstName=?," +
                    "dateOfBirth=?" +
                    " WHERE id=?;";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, client.getLastName());
            pstm.setString(2, client.getFirstName());
            pstm.setDate(3, new java.sql.Date(client.getDateOfBirth().getTime()));
            pstm.setLong(4, client.getId().get());

            pstm.executeUpdate();
        }
    }

    @Override
    public void delete(Client client) throws SQLException {
        try(Connection con = connectionFactory.getConnection()) {
            final Statement stm = con.createStatement();
            String query = "DELETE FROM clients WHERE id=" + client.getId().get() + " ;";
            stm.executeUpdate(query);
        }
    }

    @Override
    public List<Client> findByName(String name) throws SQLException {
        List<Client> result = new LinkedList<>();
        final Connection con = connectionFactory.getConnection();
        final String sql = "SELECT * FROM clients WHERE CONCAT(' ',firstName,lastName) like ?";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "%" + name + "%");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                result.add(new Client(
                        DaoUtils.GetKey(rs, "id"),
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getDate("dateOfBirth")));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
