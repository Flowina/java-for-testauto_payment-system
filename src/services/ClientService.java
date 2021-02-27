package services;

import dao.ClientDao;
import entities.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private ClientDao dao;

    public ClientService(ClientDao dao) {
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

    public void delete (long clientId) throws Exception {
        try {
            Client client = dao.findById(clientId);
            if (client == null) {
                throw new Exception("Client not found (id = " + clientId + ")");
            }
            dao.delete(client);
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

    public Client findById(long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public List<Client> getAll() {
         try {
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Client>(0);
    }
}
