package dao;

import entities.Account;
import entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao<Tid> extends CrudDao<Account, Tid> {
    List<Account> findByClient(Client client) throws SQLException;
}
