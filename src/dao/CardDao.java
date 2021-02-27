package dao;

import entities.Account;
import entities.Card;
import entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface CardDao extends CrudDao<Card> {
    List<Card> findByClient(Client client) throws SQLException;
    List<Card> findByAccount(Account account) throws SQLException;
}


