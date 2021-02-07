package services;

import dao.ClientDao;
import entities.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService<Tid> {
    private ClientDao<Tid> dao;

    public ClientService(ClientDao<Tid> dao) {
        this.dao = dao;
    }

    public void create (Client client) {
        try {
            dao.create(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update (Client client) {
        try {
            dao.update(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete (Client client) {
        try {
            dao.delete(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> findByName(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("The name must be non-empty string");
        }

        try {
            return dao.findByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Client>(0);
    }
}
