package dao;

import entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientDao extends CrudDao<Client> {
    List<Client> findByName(String name) throws SQLException;
}

