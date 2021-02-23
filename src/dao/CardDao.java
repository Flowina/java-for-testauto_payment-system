package dao;

import entities.Account;
import entities.Card;
import entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface CardDao<Tid> extends CrudDao<Card<Tid>, Tid> {
    List<Card<Tid>> findByClient(Client client) throws SQLException;
    List<Card<Tid>> findByAccount(Account account) throws SQLException;
}


