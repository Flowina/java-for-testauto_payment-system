package dao;

import entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientDao<Tid> extends CrudDao<Client, Tid> {
    List<Client> findByName(String name) throws SQLException;
}

