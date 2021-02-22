package dao;

import entities.Card;
import entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface CardDao<Tid> extends CrudDao<Card<Integer>, Tid> {
    List<Card<Integer>> findByClient(Client client) throws SQLException;
}


