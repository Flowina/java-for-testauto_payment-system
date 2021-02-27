package dao;

import entities.Account;
import entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao extends CrudDao<Account> {
    List<Account> findByClient(Client client) throws SQLException;
}
